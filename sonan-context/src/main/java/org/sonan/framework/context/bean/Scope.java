package org.sonan.framework.context.bean;

/**
 * Bean이 지원되는 scope를 정의한다.
 * annotation기반으로 등록될 bean들은 annotation이 에 따른 scope로 자동 배치될 것.
 * @default: @see {@link Scope.SINGLETON}
 * @see {@link org.sonan.framework.context.annotation.Scope}
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public enum Scope {
    SINGLETON,
    PROTOTYPE
}
