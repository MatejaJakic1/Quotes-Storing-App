package com.example.antiquote

object BackgroundSetter {
    fun getBackgrounds() : Int {
        return when ((1..27).shuffled().last()){
            1 -> R.drawable.two_seven_wp
            2 -> R.drawable.second_wp
            3 -> R.drawable.third_wp
            4 -> R.drawable.fourth_rpl_wp
            5 -> R.drawable.fifth_rpl_wp
            6 -> R.drawable.sixth_wp
            7 -> R.drawable.seventh_rpl_wp
            8 -> R.drawable.eight_rpl_wp
            9 -> R.drawable.ninth_wp
            10 -> R.drawable.tehth_rpl_wp
            11 -> R.drawable.eleventh_wp
            12 -> R.drawable.twelfth_wp
            13 -> R.drawable.thirteenth_wp
            14 -> R.drawable.fourteenth_wp
            15 -> R.drawable.fifteenth_wp
            16 -> R.drawable.seventeenth_wp
            17 -> R.drawable.eighteenth_wp
            18 -> R.drawable.nineteenth_wp
            19 -> R.drawable.twentieth_wp
            20 -> R.drawable.two_one_wp
            21 -> R.drawable.two_four_wp
            22 -> R.drawable.two_five_wp
            23 -> R.drawable.two_seven_wp
            24 -> R.drawable.two_eight_wp
            25 -> R.drawable.two_nine_wp
            26 -> R.drawable.three_one_wp
            27 -> R.drawable.three_two_wp
            else -> { R.drawable.two_seven_wp}
        }
    }
}