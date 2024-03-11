package ru.egordubina.vkproducts.ui.categories

import androidx.annotation.StringRes
import ru.egordubina.vkproducts.R

enum class CategoryType(@StringRes val title: Int, val query: String) {
    ALL(R.string.category_label__all, ""),
    SMARTPHONES(R.string.category_label__smartphones, "smartphones"),
    LAPTOPS(R.string.category_label__laptops, "laptops"),
    FRAGRANCES(R.string.category_label__fragrances, "fragrances"),
    SKINCARE(R.string.category_label__skincare, "skincare"),
    GROCERIES(R.string.category_label__groceries, "groceries"),
    HOME_DECORATION(R.string.category_label__home_decoration, "home-decoration"),
    FURNITURE(R.string.category_label__furniture, "furniture"),
    TOPS(R.string.category_label__tops, "tops"),
    WOMENS_DRESSES(R.string.category_label__wonmen_dresses, "womens-dresses"),
    WOMENS_SHOES(R.string.category_label__women_shoes, "womens-shoes"),
    MENS_SHIRTS(R.string.category_label__mens_shirts, "mens-shirts"),
    MENS_SHOES(R.string.category_label__mens_shoes, "mens-shoes"),
    MENS_WATCHES(R.string.category_label__men_watches, "mens-wathes"),
    WOMENS_WATCHES(R.string.category_label__women_watches, "womens-watches"),
    WOMENS_BAGS(R.string.category_label__women_bags, "womens-bags"),
    WOMENS_JEWELLERY(R.string.category_label__women_jewellery, "womens-jewellery"),
    SUNGLASSES(R.string.category_label__sunglasses, "sunglasses"),
    AUTOMOTIVE(R.string.category_label__automotive, "automotive"),
    MOTORCYCLE(R.string.category_label__motorcycle, "motorcycle"),
    LIGHTING(R.string.category_label__lighting, "lighting");

    companion object {
        private val map = CategoryType.entries.associateBy { it.query }
        operator fun get(query: String) = map[query]
    }
}