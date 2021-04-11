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
        getShops() {
            return axios.get('/shops', { headers: {'Content-Type': 'application/json'} })
        },
        getShop(id) {
            return axios.get(`/shops/@${id}`, { headers: {'Content-Type': 'application/json'} })
        },
        writeShop(data) {
            return axios.post('/shops', data, { headers: {'Content-Type': 'application/json'} })
        },
        updateShop(id, data) {
            return axios.put(`/shops/@${id}`, data, { headers: {'Content-Type': 'application/json'} })
        },
        deleteShop(id) {
            return axios.delete(`/shops/@${id}`, { headers: {'Content-Type': 'application/json'} })
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
                async getShops() {
                    try {
                        this.shops = await api.getShops()
                        this.shops = this.shops.data
                    } catch (e) {
                        alert('가게 목록 호출에 실패하였습니다.')
                    }
                },
                async deleteShop(e) {
                    try {
                        let id = $($(e.target).closest('li')).data('id')
                        await api.deleteShop(id)
                        this.getShops()
                    } catch (e) {
                        alert('가게 삭제에 실패하였습니다.')
                    }
                },
                goDetail(e) {
                    let id = $($(e.target).closest('li')).data('id')
                    window.location.hash = 'detail' + (id ? ('?id=' + id) : '')
                },
                init() {
                    this.getShops()
                    this.$el.on('click', '#goInsert', this.goDetail.bind(this))
                    this.$el.on('click', '.detail_btn', this.goDetail.bind(this))
                    this.$el.on('click', '.delete_btn', this.deleteShop.bind(this))
                },
                render() {
                    return `
                       <div class="entry">
                            <h1>가게 목록</h1>
                            <div>
                                <button id="goInsert">추가</button>
                            </div>
                            <div class="body">
                              {{#each shops}}
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
                async getShop() {
                    try {
                        this.shop = await api.getShop(this.params.id)
                    } catch (e) {
                        console.error(e)
                        alert('가게 호출에 실패하였습니다.')
                    }
                },
                async insertShop() {
                    try {
                        await api.writeShop({
                            name: this.$el.find('#shopName')[0].value,
                            address: this.$el.find('#shopAddress')[0].value
                        })
                        this.goHome()
                    } catch (e) {
                        alert('가게 추가에 실패하였습니다.')
                    }
                },
                async updateShop() {
                    try {
                        await api.updateShop(this.params.id, {
                            name: this.$el.find('#shopName')[0].value,
                            address: this.$el.find('#shopAddress')[0].value
                        })
                        this.goHome()
                    } catch (e) {
                        console.error(e)
                        alert('가게 수정에 실패하였습니다.')
                    }
                },
                goHome() {
                    window.location.hash = ''
                },
                init() {
                    this.$el.on('click', '#back', this.goHome.bind(this))
                    this.$el.on('click', '#update', this.updateShop.bind(this))
                    this.$el.on('click', '#insert', this.insertShop.bind(this))
                    this.params = utils.getUrlParams(window.location.hash)
                    if(this.params.id) {
                        this.getShop(this.params.id)
                    }
                },
                render() {
                    return `
                    <div class="entry">
                        <h1>가게 상세</h1>
                        <div>
                            <button id="back">돌아기기</button>
                            {{#if params.id}}
                            <button id="update">수정</button>
                            {{else}}
                            <button id="insert">추가</button>
                            {{/if}}
                        </div>
                        <div class="body">
                            <ul>shop's name : <input type="text" id="shopName" value="{{shop.name}}"></ul>
                            <ul>shop's address : <input type="text" id="shopAddress" value="{{shop.address}}"></ul>
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