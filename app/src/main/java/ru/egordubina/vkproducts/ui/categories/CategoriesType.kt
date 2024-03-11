package ru.egordubina.vkproducts.ui.categories

import androidx.annotation.StringRes
import ru.egordubina.vkproducts.R

enum class CategoriesType(@StringRes val title: Int) {
    ALL(R.string.category_label__all),
    SMARTPHONES(R.string.category_label__smartphones),
    LAPTOPS(R.string.category_label__laptops),
    FRAGRANCES(R.string.category_label__fragrances),
    SKINCARE(R.string.category_label__skincare),
    GROCERIES(R.string.category_label__groceries),
    HOME_DECORATION(R.string.category_label__home_decoration),
    FURNITURE(R.string.category_label__furniture),
    TOPS(R.string.category_label__tops),
    WOMENS_DRESSES(R.string.category_label__wonmen_dresses),
    WOMENS_SHOES(R.string.category_label__women_shoes),
    MENS_SHIRTS(R.string.category_label__mens_shirts),
    MENS_SHOES(R.string.category_label__mens_shoes),
    MENS_WATCHES(R.string.category_label__men_watches),
    WOMENS_WATCHES(R.string.category_label__women_watches),
    WOMENS_BAGS(R.string.category_label__women_bags),
    WOMENS_JEWELLERY(R.string.category_label__women_jewellery),
    SUNGLASSES(R.string.category_label__sunglasses),
    AUTOMOTIVE(R.string.category_label__automotive),
    MOTORCYCLE(R.string.category_label__motorcycle),
    LIGHTING(R.string.category_label__lighting)
}