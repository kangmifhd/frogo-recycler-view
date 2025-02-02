package com.frogobox.recycler.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.frogobox.frogolog.FLog
import com.frogobox.recycler.R
import com.frogobox.recycler.core.FrogoRvConstant.FROGO_RV_TAG

/*
 * Created by Faisal Amir
 * =========================================
 * FrogoRecyclerViewAdapter
 * Copyright (C) 29/04/2020.      
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * -----------------------------------------
 * FrogoBox Inc
 * com.frogobox.recycler.base
 * 
 */
abstract class FrogoRecyclerViewAdapter<T> :
    RecyclerView.Adapter<FrogoRecyclerViewHolder<T>>() {

    var hasEmptyView = false
    var hasMultiHolder = false
    var hasNestedView = false

    protected var viewListener: FrogoRecyclerViewListener<T>? = null

    protected val listPosition = HashMap<Int, Int>()
    protected val sharedPool = RecyclerView.RecycledViewPool()

    protected val frogoHolder = mutableListOf<FrogoHolder<T>>()
    protected val listData = mutableListOf<T>()
    protected val listDataNested = mutableListOf<MutableList<T>>()
    protected var listCount = 0

    protected var layoutRv: Int = 0
    protected var customLayoutRestId: Int = 0
    protected var emptyLayoutResId: Int = R.layout.frogo_container_empty_view

    fun bindNestedHolder() {

    } // component view

    override fun getItemCount(): Int {

        return if (hasNestedView) {
            listDataNested.size
        } else {
            if (hasMultiHolder) {
                if (hasEmptyView) {
                    listCount = if (frogoHolder.size == 0) {
                        1
                    } else {
                        frogoHolder.size
                    }
                    listCount
                } else {
                    frogoHolder.size
                }
            } else {
                if (hasEmptyView) {
                    listCount = if (listData.size == 0) {
                        1
                    } else {
                        listData.size
                    }
                    listCount
                } else {
                    listData.size
                }
            }
        }
    }

    override fun onBindViewHolder(holder: FrogoRecyclerViewHolder<T>, position: Int) {

        FLog.d("$FROGO_RV_TAG - hasMultiHolder : $hasMultiHolder")
        FLog.d("$FROGO_RV_TAG - hasEmptyView : $hasEmptyView")
        FLog.d("$FROGO_RV_TAG - listCount : $listCount")
        FLog.d("$FROGO_RV_TAG - frogoHolder : ${frogoHolder.size}")
        FLog.d("$FROGO_RV_TAG - listData : ${listData.size}")

        if (hasNestedView) {
            bindNestedHolder()
        } else {
            if (hasMultiHolder) {
                if (hasEmptyView) {
                    if (frogoHolder.size != 0) {
                        holder.bindItem(frogoHolder[position].data, frogoHolder[position].listener)
                    }
                } else {
                    holder.bindItem(frogoHolder[position].data, frogoHolder[position].listener)
                }
            } else {
                if (hasEmptyView) {
                    if (listData.size != 0) {
                        holder.bindItem(listData[position], viewListener)
                    }
                } else {
                    holder.bindItem(listData[position], viewListener)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasMultiHolder) {
            if (frogoHolder.size != 0) {
                frogoHolder[position].option
            } else {
                super.getItemViewType(position)
            }
        } else {
            super.getItemViewType(position)
        }
    }

    override fun onViewRecycled(holder: FrogoRecyclerViewHolder<T>) {
        if (hasNestedView) {
            val position = holder.adapterPosition
            val nestedHolder = holder as FrogoNestedHolder<T>
            listPosition[position] = nestedHolder.getLinearLayoutManager().findFirstVisibleItemPosition()
        }
        super.onViewRecycled(holder)
    }

    fun layoutHandling() {
        if (hasMultiHolder) {
            if (customLayoutRestId != 0) {
                layoutRv = if (frogoHolder.isNotEmpty()) {
                    customLayoutRestId
                } else {
                    emptyLayoutResId
                }
            }
        } else {
            if (customLayoutRestId != 0) {
                layoutRv = if (listData.isNotEmpty()) {
                    customLayoutRestId
                } else {
                    emptyLayoutResId
                }
            }
        }
    }

    fun viewLayout(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(layoutRv, parent, false)
    }

    fun viewLayout(parent: ViewGroup, layoutResId: Int): View {
        customLayoutRestId = layoutResId
        layoutHandling()
        return LayoutInflater.from(parent.context).inflate(layoutRv, parent, false)
    }

    fun setupNestedView() {
        hasNestedView = true
    }

    fun setupMultiHolder() {
        hasMultiHolder = true
    }

    fun setupEmptyView(emptyView: Int?) {
        hasEmptyView = true
        if (emptyView != null) {
            emptyLayoutResId = emptyView
        }
        layoutHandling()
    }

    fun setupDataNested(data: List<MutableList<T>>?) {
        this.listDataNested.clear()

        if (data != null) {
            this.listDataNested.addAll(data)
        }
    }

    fun setupData(data: List<T>?) {
        this.listData.clear()

        if (data != null) {
            this.listData.addAll(data)
        }
    }

    fun setupListener(listener: FrogoRecyclerViewListener<T>?) {
        if (listener != null) {
            viewListener = listener
        }
    }

    fun setupCustomLayout(customViewId: Int) {
        customLayoutRestId = customViewId
    }

    fun setupRequirement(
        customViewId: Int,
        data: List<T>?,
        listener: FrogoRecyclerViewListener<T>?
    ) {

        if (listener != null) {
            viewListener = listener
        }

        this.listData.clear()

        if (data != null) {
            this.listData.addAll(data)
        }

        customLayoutRestId = customViewId
        layoutHandling()
    }

    fun setupRequirement(viewHolder: List<FrogoHolder<T>>) {
        frogoHolder.addAll(viewHolder)
    }

}