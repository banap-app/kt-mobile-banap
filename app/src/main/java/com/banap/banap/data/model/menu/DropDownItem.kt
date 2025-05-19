package com.banap.banap.data.model.menu

data class DropDownItem (
    val option: MenuOption,
    val optionSelected: (DropDownItem) -> Unit
)