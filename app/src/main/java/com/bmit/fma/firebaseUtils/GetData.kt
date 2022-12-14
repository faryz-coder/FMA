package com.bmit.fma.firebaseUtils

import android.util.Log
import com.bmit.fma.FixNotation.LOG
import com.bmit.fma.admin.UserList
import com.bmit.fma.canteen.ItemList
import com.bmit.fma.dialogs.ItemOrder
import com.bmit.fma.interfaceListener.ItemCallback
import com.bmit.fma.student.ListMenu
import com.bmit.fma.student.ListOrder
import com.google.firebase.firestore.Query.Direction
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GetData {

    private val db = Firebase.firestore

    fun itemList(callback: ItemCallback) {

        val listItem = mutableListOf<ItemList>()

        val doc = db.collection("canteen").document("menu").collection("list").orderBy("type", Direction.DESCENDING)
            doc.get()
                .addOnSuccessListener { document ->
                    document.forEach { item ->
                        listItem.add(
                            ItemList(
                                item["name"].toString(),
                                item["imageUrl"].toString(),
                                item["calories"].toString(),
                                item["price"].toString(),
                                item["status"].toString(),
                                item["type"].toString(),
                                item.id
                            )
                        )

                    }
                    callback.returnList(listItem)
                }
                .addOnFailureListener {
                    Log.d(LOG, "Failed to retrieve item")
                }
    }

    fun getItemInfo(itemId: String, callback: ItemCallback) {
        val doc = db.collection("canteen").document("menu").collection("list").document(itemId)
        doc.get()
            .addOnSuccessListener { item ->
                callback.onItemInfo(item)
                Log.d(LOG, "info retrieved")
            }
    }

    fun getStaffInfo(staffId: String, callback: ItemCallback) {
        val doc = db.collection("Login").document(staffId)
        doc.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    callback.onItemInfo(document)
                } else {
                    //no document
                    Log.d(LOG, "No info: $staffId")
                }
            }
    }

    fun staffList(callback: ItemCallback) {
        val doc = db.collection("Login").whereEqualTo("type", "staff")
        val listStaff = mutableListOf<UserList>()

        doc.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(LOG, "document: ${document.documents}")
                    document.documents.forEach { profile ->
                        Log.d(LOG, "document: ${profile["name"]}")
                        listStaff.add(
                            UserList(
                                profile["name"].toString(),
                                profile["email"].toString(),
                                profile.id,
                                profile["studentId"].toString(),
                                profile["type"].toString()
                            )
                        )
                    }
                    callback.returnList(listStaff)
                }
            }
    }

    fun studentList(callback: ItemCallback) {
        val listStudent = mutableListOf<UserList>()
        val doc = db.collection("Login").whereEqualTo("type", "student")
        doc.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(LOG, "document: ${document.documents}")
                    document.documents.forEach { profile ->
                        Log.d(LOG, "document: ${profile["name"]}")
                        listStudent.add(
                            UserList(
                                profile["name"].toString(),
                                profile["email"].toString(),
                                profile.id,
                                profile["studentId"].toString(),
                                profile["type"].toString()
                            )
                        )
                    }
                    callback.returnList(listStudent)
                }
            }
    }

    fun getStudentInfo(studentId: String, callback: ItemCallback) {
        val doc = db.collection("Login").document(studentId)
        doc.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    callback.onItemInfo(document)
                } else {
                    //no document
                    Log.d(LOG, "No info: $studentId")
                }
            }
            .addOnFailureListener {
                Log.d(LOG, "Failed to retrieve info: $it")
            }
    }

    fun getMenu(callback: ItemCallback, itemOrdered: MutableList<ItemOrder>)  {
        val foodMenu = mutableListOf<ListMenu>()
        val drinkMenu = mutableListOf<ListMenu>()
        val snackMenu = mutableListOf<ListMenu>()

        db.collection("canteen").document("menu").collection("list")
            .orderBy("type", Direction.DESCENDING)
            .get()
            .addOnSuccessListener { document ->
                document.forEach {
                    val type = it["type"].toString()
                    val imageURL = it["imageUrl"].toString()
                    val name = it["name"].toString()
                    val price = it["price"].toString()
                    val calories = it["calories"].toString()
                    val status = it["status"].toString()
                    val itemId = it.id

                    var quantity = "0"
                    val isExist = itemOrdered.filter { it.itemId == itemId }
                    if (isExist.isNotEmpty()) {
                        quantity = isExist.first().quantity
                    }
                    if(!status.contains("disabled")) {
                        when (type) {
                            "Food" -> foodMenu.add(
                                ListMenu(
                                    name,
                                    imageURL,
                                    price,
                                    calories,
                                    status,
                                    type,
                                    itemId,
                                    quantity
                                )
                            )
                            "Drink" -> drinkMenu.add(
                                ListMenu(
                                    name,
                                    imageURL,
                                    price,
                                    calories,
                                    status,
                                    type,
                                    itemId,
                                    quantity
                                )
                            )
                            "Snack" -> snackMenu.add(
                                ListMenu(
                                    name,
                                    imageURL,
                                    price,
                                    calories,
                                    status,
                                    type,
                                    itemId,
                                    quantity
                                )
                            )
                        }
                    }
                }
                callback.returnMenu(foodMenu, drinkMenu, snackMenu)
            }
    }

    fun getOrderedItem(itemOrdered: MutableList<ItemOrder>, callback: ItemCallback) {
        val orderList = mutableListOf<ListMenu>()
        var totalPrice = 0.0
        var totalCal = 0

        db.collection("canteen").document("menu").collection("list")
            .orderBy("type", Direction.DESCENDING)
            .get()
            .addOnSuccessListener { document ->
                document.forEach {
                    val type = it["type"].toString()
                    val imageURL = it["imageUrl"].toString()
                    val name = it["name"].toString()
                    val price = it["price"].toString()
                    val calories = it["calories"].toString()
                    val status = it["status"].toString()
                    val itemId = it.id

                    val isExist = itemOrdered.filter { it.itemId == itemId }

                    if (isExist.isNotEmpty()) {
                        val quantity = isExist.first().quantity
                        orderList.add(ListMenu(name, imageURL, price, calories, status, type, itemId, quantity))
                        totalPrice += price.toDouble() * quantity.toInt()
                        totalCal += calories.toInt() * quantity.toInt()
                    }
                }
                callback.returnOrderItemList(orderList, totalPrice, totalCal)
            }
    }

    fun getOrderedHistory(callback: ItemCallback, studentId: String) {
        val listHistory = mutableListOf<ListOrder>()

        db.collection("student").document(studentId).collection("order")
            .get()
            .addOnSuccessListener { document ->
                document.forEach { field ->
                    listHistory.add(ListOrder(
                        field["order"].toString(),
                        field["studentId"].toString(),
                        field["status"].toString(),
                        field["total"].toString(),
                        field["date"].toString(),
                        field.id
                    ))
                }
                callback.returnList(listHistory)
            }
    }

    fun getOrderedList(callback: ItemCallback) {
        val listOrder = mutableListOf<ListOrder>()

        db.collection("canteen").document("order").collection("list")
            .get()
            .addOnSuccessListener { document ->
                document.forEach { field ->
                    listOrder.add(ListOrder(
                        field["order"].toString(),
                        field["studentId"].toString(),
                        field["status"].toString(),
                        field["total"].toString(),
                        field["date"].toString(),
                        field.id
                    ))
                }
                callback.returnList(listOrder)
            }
    }

    fun getSpecificOrderItem(itemId: String, callback: ItemCallback) {
        val listOrder = mutableListOf<ListOrder>()

        db.collection("canteen").document("order").collection("list").document(itemId)
            .get()
            .addOnSuccessListener { document ->
                listOrder.add(ListOrder(
                    document["order"].toString(),
                    document["studentId"].toString(),
                    document["status"].toString(),
                    document["total"].toString(),
                    document["date"].toString(),
                    document.id
                ))
                callback.returnList(listOrder)
            }
    }

}