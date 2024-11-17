package com.example.kotlinquizgraduationproject.ui.fakePackage

import com.example.kotlinquizgraduationproject.repository.ApiRepository

class FakeQuizRepository {
    fun test(): ApiRepository {
        return ApiRepository(FakeQuizApi())
    }
}