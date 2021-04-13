(() => {
    const utils = {
        getUrlParams(search) {
            let hashes = search.slice(search.indexOf('?') + 1).split('&')
            return hashes.reduce((params, hash) => {
                let [key, val] = hash.split('=')
                return Object.assign(params, {[key]: decodeURIComponent(val)})
            }, {})
        }
    }

    const api = {
        getUsers() {
            return axios.get('/users', { headers: {'Content-Type': 'application/json'} })
        },
        getUser(id) {
            return axios.get(`/users/@${id}`, { headers: {'Content-Type': 'application/json'} })
        },
        writeUser(data) {
            return axios.post('/users', data, { headers: {'Content-Type': 'application/json'} })
        },
        updateUser(id, data) {
            return axios.put(`/users/@${id}`, data, { headers: {'Content-Type': 'application/json'} })
        },
        deleteUser(id) {
            return axios.delete(`/users/@${id}`, { headers: {'Content-Type': 'application/json'} })
        }
    }

    const baseView = {
        async _load() {
            await this._init()
            await this._render()
            this._loadComplete = true
        },
        async _init() {
            this.$el = $(this.el)
            await this.init()
        },
        async _render() {
            this.$el.html(Handlebars.compile(await this.render())(this))
        }
    }

    const views = {
        main :(() => {
            return {
                el: '#container',
                async getUsers() {
                    try {
                        this.users = await api.getUsers()
                        this.users = this.users.data
                    } catch (e) {
                        alert('고객 목록 호출에 실패하였습니다.')
                    }
                },
                async deleteUser(e) {
                    try {
                        let id = $($(e.target).closest('li')).data('id')
                        await api.deleteUser(id)
                        this.getUsers()
                    } catch (e) {
                        alert('고객 삭제에 실패하였습니다.')
                    }
                },
                goDetail(e) {
                    let id = $($(e.target).closest('li')).data('id')
                    window.location.hash = 'detail' + (id ? ('?id=' + id) : '')
                },
                init() {
                    this.getUsers()
                    this.$el.on('click', '#goInsert', this.goDetail.bind(this))
                    this.$el.on('click', '.detail_btn', this.goDetail.bind(this))
                    this.$el.on('click', '.delete_btn', this.deleteUser.bind(this))
                },
                render() {
                    return `
                       <div class="entry">
                            <h1>고객 목록</h1>
                            <div>
                                <button id="goInsert">추가</button>
                            </div>
                            <div class="body">
                              {{#each users}}
                              <ul>
                                <li>{{id}}</li>
                                <li>{{name}}</li>
                                <li>{{address}}</li>
                                <li data-id="{{id}}">
                                    <button class="detail_btn">수정</button>
                                    <button class="delete_btn">삭제</button>
                                </li>
                              </ul>
                              {{/each}}
                            </div>
                        </div>`
                }
            }
        })(),
        detail : (() => {
            return {
                el: '#container',
                async getUser() {
                    try {
                        this.user = await api.getUser(this.params.id)
                    } catch (e) {
                        console.error(e)
                        alert('고객 호출에 실패하였습니다.')
                    }
                },
                async insertUser() {
                    try {
                        await api.writeUser({
                            name: this.$el.find('#userName')[0].value,
                            address: this.$el.find('#userAddress')[0].value
                        })
                        this.goHome()
                    } catch (e) {
                        alert('고객 추가에 실패하였습니다.')
                    }
                },
                async updateUser() {
                    try {
                        await api.updateUser(this.params.id, {
                            name: this.$el.find('#userName')[0].value,
                            address: this.$el.find('#userAddress')[0].value
                        })
                        this.goHome()
                    } catch (e) {
                        console.error(e)
                        alert('고객 수정에 실패하였습니다.')
                    }
                },
                goHome() {
                    window.location.hash = ''
                },
                init() {
                    this.$el.on('click', '#back', this.goHome.bind(this))
                    this.$el.on('click', '#update', this.updateUser.bind(this))
                    this.$el.on('click', '#insert', this.insertUser.bind(this))
                    this.params = utils.getUrlParams(window.location.hash)
                    if(this.params.id) {
                        this.getUser(this.params.id)
                    }
                },
                render() {
                    return `
                    <div class="entry">
                        <h1>고객 상세</h1>
                        <div>
                            <button id="back">돌아기기</button>
                            {{#if params.id}}
                            <button id="update">수정</button>
                            {{else}}
                            <button id="insert">추가</button>
                            {{/if}}
                        </div>
                        <div class="body">
                            <ul>user's name : <input type="text" id="userName" value="{{user.name}}"></ul>
                            <ul>user's address : <input type="text" id="userAddress" value="{{user.address}}"></ul>
                        </div>
                    </div>
                    `
                }
            }
        })()
    }

    const router = (hash) => {
        if(hash === '' || hash === '#') {
            return views.main
        } else if (hash.includes('#detail')) {
            return views.detail
        }
    }

    const proxy = (route) => {
        let assign = Object.assign({}, route, baseView)
        return new Proxy(assign, {
            set(target, key, value) {
                target[key] = value
                if(assign._loadComplete) {
                    assign._render()
                }
            }
        })
    }

    const start = () => {
        if(this._view) {
            this._view.$el.off()
        }
        this._view = proxy(router(window.location.hash))
        this._view._load()
    }
    window.addEventListener("hashchange", start, false)
    start()
})()