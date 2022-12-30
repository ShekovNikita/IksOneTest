package com.sheniv.iksone.helpers

import com.sheniv.iksone.model.Person
import com.sheniv.iksone.room.UserRoom

class Convertor {

    fun fromPersonToUser(person: Person) =
        UserRoom(
            age = person.age,
            name = person.name,
            favorite = 0
        )
}