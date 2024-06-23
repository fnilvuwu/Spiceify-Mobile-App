package com.example.herbs

object DataSource {
    var spices: ArrayList<Spice> = generateDummySpices()

    private fun generateDummySpices(): ArrayList<Spice> {
        val spices: ArrayList<Spice> = ArrayList()
        spices.add(Spice(
            "Jahe",
            "Umbi Umbian",
            "Manfaat :\n" +
                    "1. Mengurangi Reaksi Inflamasi. \n" +
                    "2. Mengendalikan Kadar Gula Darah. \n" +
                    "3. Meredakan Mual.\n" +
                    "\n" +
                    "Diolah menjadi :\n" +
                    "1. Tim Ayam Jahe\n" +
                    "2. Ayam Goreng Jahe\n" +
                    "3. Ginger Lemon Honey Tonic\n" +
                    "4. Lemon Jahe madu \n" +
                    "5. Minuman Jahe Susu",
            R.drawable.detail_jahe, R.drawable.item_jahe
        ))
        spices.add(Spice("Lengkuas", "Umbi Umbian", "Manfaat :\n" +
                "1. Meminimalisir radikal bebas.\n" +
                "2. Meningkatkan kesehatan rambut.\n" +
                "3. Lengkuas meningkatkan kesuburan pria.\n" +
                "4. Melindungi dari infeksi. \n" +
                "5. Mengatasi radang sendi.\n" +
                "\n" +
                "Diolah menjadi :\n" +
                "1. Sayur Bayam Lengkuas\n" +
                "2. Ayam Goreng Laos\n" +
                "3. Tempe Lengkuas\n" +
                "4. Cumi Goreng Lengkuas\n" +
                "5. Dadar Jagung Bumbu Lengkuas", R.drawable.detail_lengkuas, R.drawable.item_lengkuas))
        spices.add(Spice("Daun Salam", "Daun", "Manfaat:\n" +
                "1. Menurunkan kadar kolesterol\n" +
                "2. Meredakan gangguan pencernaan\n" +
                "3. Mengurangi stres\n" +
                "\n" +
                "\n" +
                "Diolah menjadi :\n" +
                "1. Nasi Uduk\n" +
                "2. Semur\n" +
                "3. Sop Ayam", R.drawable.detail_daunsalam, R.drawable.item_daunsalam))
        spices.add(Spice("Daun Jeruk", "Daun", "Manfaat:\n" +
                "1. Menyegarkan napas\n" +
                "2. Mengatasi stres\n" +
                "3. Memiliki sifat anti-inflamasi\n" +
                "\n" +
                "\n" +
                "Diolah menjadi :\n" +
                "1. Ayam Penyet\n" +
                "2. Soto\n" +
                "3. Nasi Goreng", R.drawable.detail_daunjeruk, R.drawable.item_daunjeruk))
        spices.add(Spice("Ketumbar", "Biji", "Manfaat : \n" +
                "1. Menurunkan kadar kolesterol\n" +
                "2. Meredakan gangguan pencernaan\n" +
                "3. Mengurangi stres\n"+
                "\n" +
                "\n" +
                "Diolah menjadi :\n" +
                "1. Gulai\n" +
                "2. Sup\n" +
                "3. Kare\n"
            , R.drawable.detail_ketumbar, R.drawable.item_ketumbar))
        spices.add(Spice("Kencur", "Umbi Umbian", "Manfaat:\n" +
                "1. Meredakan batuk\n" +
                "2. Mengatasi peradangan\n" +
                "3. Meningkatkan nafsu makan\n" +
                "\n" +
                "\n" +
                "Diolah menjadi :\n" +
                "1. Jamu beras kencur\n" +
                "2. Soto\n" +
                "3. Pepes", R.drawable.detail_kencur, R.drawable.item_kencur))
        spices.add(Spice("Cengkeh", "Bunga", "Manfaat:\n" +
                "1. Mengatasi sakit gigi\n" +
                "2. Memiliki sifat antioksidan\n" +
                "3. Meredakan mual\n" +
                "\n" +
                "\n" +
                "Diolah menjadi :\n" +
                "1. Rendang\n" +
                "2. Kari\n" +
                "3. Minuman hangat (teh atau wedang)", R.drawable.detail_cengkeh, R.drawable.item_cengkeh))
        spices.add(Spice("Kayu Manis", "Batang", "Manfaat :\n" +
                "1. Menurunkan berat badan.\n" +
                "2. Baik untuk pengidap diabetes.\n" +
                "3. Meredakan kram menstruasi.\n" +
                "4. Meningkatkan kekebalan tubuh.\n" +
                "5. Mengatasi kista ovarium. \n" +
                "\n" +
                "\n" +
                "Diolah menjadi :\n" +
                "1. Wedang Kopi Kayu Manis\n" +
                "2. Teh Susu Kayu Manis\n" +
                "3. Bolu Pisang Kayu Manis\n" +
                "4. Bajigur Kayu Manis\n" +
                "5. Pisang Goreng Wijen Kayu Manis", R.drawable.detail_kayumanis, R.drawable.item_kayumanis))
        spices.add(Spice("Andaliman", "Buah", "Manfaat :\n" +
                "1. Meningkatkan imunitas tubuh. \n" +
                "2. Mendukung kesehatan tulang. \n" +
                "3. Menyembuhkan luka. \n" +
                "4. Mencegah anemia. \n" +
                "5. Mengatasi sakit gigi. \n" +
                "\n" +
                "\n" +
                "Diolah menjadi :\n" +
                "1. Ayam Masak Andaliman\n" +
                "2. Mie Gomak Saus Andaliman\n" +
                "3. Sambel Andaliman\n" +
                "4. Nasi Goreng Andaliman\n" +
                "5. Nila Andaliman", R.drawable.detail_andaliman, R.drawable.item_andaliman))
        spices.add(Spice("Kemiri", "Biji", "Manfaat:\n" +
                "1. Menyehatkan rambut\n" +
                "2. Meningkatkan fungsi otak\n" +
                "3. Menurunkan kolesterol\n" +
                "4. Menjaga kesehatan pencernaan\n" +
                "5. Meningkatkan daya tahan tubuh\n" +
                "\n" +
                "\n" +
                "Diolah menjadi :\n" +
                "1. Sate\n" +
                "2. Rendang\n" +
                "3. Keripik singkong\n" +
                "4. Ikan goreng", R.drawable.detail_kemiri, R.drawable.item_kemiri))
        spices.add(Spice("Kunyit", "Umbi umbian", "Manfaat :\n" +
                "1. Mengobati Radang.\n" +
                "2. Mengatasi Perut yang Kembung.\n" +
                "3. Mengurangi Nyeri saat Haid.\n" +
                "4. Obat Alergi. \n" +
                "5. Menangkal Bakteri Jahat. \n" +
                "\n" +
                "\n" +
                "Diolah menjadi :\n" +
                "1. Teh Kunyit\n" +
                "2. Nasi Goreng Kunyit\n" +
                "3. Jamu Kunyit Asam Sirih\n" +
                "4. Kunyit Asem Jeruk Peres\n" +
                "5. Es Jahe Kunyit Asem", R.drawable.detail_kunyit,R.drawable.item_kunyit))
        spices.add(Spice("Pala", "Biji", "Manfaat:\n" +
                "1. Membantu pencernaan\n" +
                "2. Menurunkan kolesterol\n" +
                "3. Mengontrol gula darah\n" +
                "4. Menjaga kesehatan jantung\n" +
                "\n" +
                "Diolah menjadi :\n" +
                "1. Gulai\n" +
                "2. Sup\n" +
                "3. Kare", R.drawable.detail_pala, R.drawable.item_pala))
        return spices
    }

}