package com.bmit.fma.interfaceListener

interface LoginCallback {
    fun isStudent(status: Boolean, studentId: String, type: String) {}
    fun isAdmin(status: Boolean, adminEmail: String, type: String) {}
    fun isCanteen(status: Boolean, staffEmail: String, type: String) {}
}