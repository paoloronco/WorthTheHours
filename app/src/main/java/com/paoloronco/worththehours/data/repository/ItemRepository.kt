package com.paoloronco.worththehours.data.repository

import com.paoloronco.worththehours.data.local.ItemDao
import com.paoloronco.worththehours.model.Item
import kotlinx.coroutines.flow.Flow

class ItemRepository(private val itemDao: ItemDao) {

    fun getItems(): Flow<List<Item>> = itemDao.getItems()

    suspend fun insertItem(item: Item) {
        itemDao.insertItem(item)
    }

    suspend fun deleteItem(item: Item) {
        itemDao.deleteItem(item)
    }
}
