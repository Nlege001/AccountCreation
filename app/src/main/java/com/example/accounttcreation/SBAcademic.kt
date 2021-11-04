package com.example.accounttcreation

class SBAcademic {
    private var Email: String? = null
    private var Faculty: String? = null
    private var Name: String? = null
    private var Title: String? = null

    fun Academic() {}

    fun Academic(email: String?, faculty: String?, name: String?, title: String?) {
        Email = email
        Faculty = faculty
        Name = name
        Title = title
    }

    fun getEmail(): String? {
        return Email
    }

    fun setEmail(email: String?) {
        Email = email
    }

    fun getFaculty(): String? {
        return Faculty
    }

    fun setFaculty(faculty: String?) {
        Faculty = faculty
    }

    fun getName(): String? {
        return Name
    }

    fun setName(name: String?) {
        Name = name
    }

    fun getTitle(): String? {
        return Title
    }

    fun setTitle(title: String?) {
        Title = title
    }
}

