package com.example.chempionat

import com.example.chempionat.models.PersonModel

/**
 * Person - отдельный объект, который содержит в себе информацию о пользователе, которая будет
 * неоднократно использоваться в приложении
 */
object Person {
    var person: PersonModel? = null
    lateinit var token: String
    lateinit var password: String
    lateinit var email: String
}