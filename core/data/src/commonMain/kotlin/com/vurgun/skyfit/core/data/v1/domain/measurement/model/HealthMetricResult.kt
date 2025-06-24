package com.vurgun.skyfit.core.data.v1.domain.measurement.model


sealed class HealthMetricResult(
    open val label: String,
    open val score: Float?,
    open val comment: String,
    open val emoji: String,
    open val description: String
) {

    data class BMI(
        override val score: Float?
    ) : HealthMetricResult(
        score = score,
        label = "Vücut Kitle İndeksi (BMI)",
        comment = interpret(score),
        emoji = interpretEmoji(score),
        description = """
        Vücut Kitle İndeksi, kilonuzun boyunuza oranla ideal seviyede olup olmadığını gösterir.
        • 18.5 altında: Zayıf – bağışıklık ve enerji sorunları yaşanabilir.
        • 18.5–24.9: Normal – sağlıklı ve ideal bir aralık.
        • 25–29.9: Kilolu – yağlanma başlayabilir, dikkat edilmeli.
        • 30 ve üzeri: Obezite – kronik hastalık riskleri artar.
        
        Bu oran spor ve sağlıklı yaşamın temel göstergelerinden biridir.
    """.trimIndent()
    ) {
        companion object {
            fun interpret(value: Float?): String = when {
                value == null -> "Veri yok."
                value < 18.5 -> "Zayıf: Kas ve kilo kazanımına odaklanmalısınız."
                value < 25 -> "Normal: Harika gidiyorsunuz, bu formu koruyun!"
                value < 30 -> "Kilolu: Dengeli beslenme ve egzersiz önerilir."
                else -> "Obezite: Sağlık açısından riskli, dikkat edilmesi gerekir."
            }

            fun interpretEmoji(value: Float?): String = when {
                value == null -> "❔"
                value < 18.5 -> "🟡"
                value < 25 -> "🟢"
                value < 30 -> "🟠"
                else -> "🔴"
            }
        }
    }

    data class WHR(
        override val score: Float?
    ) : HealthMetricResult(
        score = score,
        label = "Bel-Kalça Oranı (WHR)",
        comment = interpret(score),
        emoji = interpretEmoji(score),
        description = """
        WHR, vücut yağınızın nasıl dağıldığını gösterir. 
        • Kadınlar için ideal ≤ 0.85 
        • Erkekler için ideal ≤ 0.90
        
        • 0.80 altında: Harika – düşük viseral yağ oranı.
        • 0.80–0.90: Orta – dikkatli olunmalı.
        • 0.90 üzeri: Yüksek risk – kardiyovasküler riskler artar.
        
        Sporla bu oran iyileştirilebilir.
    """.trimIndent()
    ) {
        companion object {
            fun interpret(value: Float?): String = when {
                value == null -> "Veri yok."
                value < 0.80f -> "Harika: Düşük riskli yağ dağılımı."
                value <= 0.90f -> "Orta düzey risk: Sporla desteklenmeli."
                else -> "Yüksek risk: Sağlık açısından dikkat edilmeli."
            }

            fun interpretEmoji(value: Float?): String = when {
                value == null -> "❔"
                value < 0.80f -> "🟢"
                value <= 0.90f -> "🟡"
                else -> "🔴"
            }
        }
    }

    data class HLS(
        override val score: Float?
    ) : HealthMetricResult(
        score = score,
        label = "Genel Sağlık Skoru (HLS)",
        comment = interpret(score),
        emoji = interpretEmoji(score),
        description = """
        HLS (Genel Sağlık Skoru), birden fazla ölçüm değerinizin (BMI, WHR, Şınav Sayısı, Simetri gibi) birleşimiyle oluşturulan genel sağlık göstergesidir.

        Bu skor; vücut kompozisyonunuz, dayanıklılık seviyeniz ve yapısal dengeniz göz önüne alınarak hesaplanır.

        Skor Aralıkları:
        • 0 – 49   : 🔴 Düşük – Gelişime açık, yeni bir başlangıç için fırsat.
        • 50 – 74  : 🟡 Orta – Güzel bir temeliniz var, istikrarlı ilerleyin.
        • 75 – 90  : 🟢 İyi – Sağlıklı, dengeli bir yaşam tarzı oluşturmuşsunuz.
        • 91 – 100 : 🌟 Mükemmel – Örnek alınacak bir formdasınız!

        Bu skor, zamanla yaptığınız antrenmanlar ve gelişen ölçümlerle artar veya azalır. Hedefiniz, sürdürülebilir şekilde daha iyiye gitmek olmalı.
    """.trimIndent()
    ) {
        companion object {
            fun interpret(value: Float?): String = when {
                value == null -> "Veri yok."
                value < 50f -> "Düşük: Daha iyi bir yaşam için başlangıç fırsatı."
                value < 75f -> "Orta: Gelişmeye açıksınız, istikrarlı olun."
                value < 91f -> "İyi: Sağlıklı ve formdasınız, devam!"
                else -> "Mükemmel: Spor hayatınızın bir parçası olmuş!"
            }

            fun interpretEmoji(value: Float?): String = when {
                value == null -> "❔"
                value < 50f -> "🔴"
                value < 75f -> "🟡"
                value < 91f -> "🟢"
                else -> "🌟"
            }
        }
    }

    data class ArmSymmetry(
        override val score: Float? // fark: abs(right - left)
    ) : HealthMetricResult(
        score = score,
        label = "Kol Simetrisi",
        comment = interpret(score),
        emoji = interpretEmoji(score),
        description = """
        Kol Simetrisi, sağ ve sol kol çevresi arasındaki farkı ölçer. Bu fark ne kadar küçükse, kas gelişiminiz o kadar dengeli demektir.

        Dengeli kollar:
        • Daha estetik bir görünüm sağlar.
        • Postürünüzü destekler.
        • Kuvvet dağılımını dengeler.
        • Sakatlanma riskini azaltır.

        Değerlendirme Aralıkları:
        • 0 – 1 cm   : 🟢 Mükemmel simetri – Kollarınız dengeli gelişmiş.
        • 1 – 2.5 cm : 🟡 Orta düzey fark – Gözlemlenmeli ama büyük risk değil.
        • 2.5 cm+    : 🔴 Belirgin fark – Asimetrik egzersizlerle dengelenmeli.

        Tavsiye: Eğer bir kolunuz diğerinden belirgin şekilde büyükse, eksik kalan tarafı hedefleyen izole egzersizler ekleyerek dengeyi zamanla sağlayabilirsiniz.
    """.trimIndent()
    ) {
        companion object {
            fun interpret(value: Float?): String = when {
                value == null -> "Veri yok."
                value <= 1f -> "Mükemmel simetri!"
                value <= 2.5f -> "Küçük farklar var, dikkat edilebilir."
                else -> "Belirgin fark: Dengeleyici egzersizler önerilir."
            }

            fun interpretEmoji(value: Float?): String = when {
                value == null -> "❔"
                value <= 1f -> "🟢"
                value <= 2.5f -> "🟡"
                else -> "🔴"
            }
        }
    }

    data class LegSymmetry(
        override val score: Float? // fark: abs(right - left)
    ) : HealthMetricResult(
        score = score,
        label = "Bacak Simetrisi",
        comment = interpret(score),
        emoji = interpretEmoji(score),
        description = """
        Bacak Simetrisi, sağ ve sol bacak çevresi arasındaki farkı ölçer. Bu ölçüm, alt vücut kas gelişiminizin ne kadar dengeli olduğunu gösterir.

        Simetrik bacaklar:
        • Duruş bozukluklarını önler.
        • Yürüme ve koşma gibi hareketlerde verimliliği artırır.
        • Sakatlanma riskini azaltır.
        • Estetik görünüm kazandırır.

        Değerlendirme Aralıkları:
        • 0 – 1 cm   : 🟢 Mükemmel simetri – Bacaklarınız dengeli gelişmiş.
        • 1 – 2.5 cm : 🟡 Orta seviye fark – Dengelemek için fırsat var.
        • 2.5 cm+    : 🔴 Belirgin fark – Tek taraflı yüklenme olabilir, dikkat edilmeli.

        Tavsiye: Squat, lunge ve tek taraflı (unilateral) egzersizlerle eksik kalan bacak kası hedeflenerek denge sağlanabilir. Dengeli alt vücut, uzun vadede performansın ve sağlığın temelidir.
    """.trimIndent()
    ) {
        companion object {
            fun interpret(value: Float?): String = when {
                value == null -> "Veri yok."
                value <= 1f -> "Harika: Bacaklarınız çok dengeli gelişmiş!"
                value <= 2.5f -> "Orta: Küçük farklar var, gözlemlenebilir."
                else -> "Belirgin fark: Dengeleyici egzersizler yapılmalı."
            }

            fun interpretEmoji(value: Float?): String = when {
                value == null -> "❔"
                value <= 1f -> "🟢"
                value <= 2.5f -> "🟡"
                else -> "🔴"
            }
        }
    }

    data class ChestWaistRatio(
        override val score: Float?
    ) : HealthMetricResult(
        score =score,
        label = "Göğüs / Bel Oranı",
        comment = interpret(score),
        emoji = interpretEmoji(score),
        description = """
        Göğüs / Bel Oranı, üst vücut kaslarınızın (özellikle göğüs ve sırt) bel çevrenize göre gelişimini gösterir. Bu oran, estetik görünümün yanı sıra postür ve fonksiyonellik açısından da önemlidir.

        Yüksek oran:
        • Daha üçgen ve sportif bir vücut hattı anlamına gelir.
        • Core (merkez bölge) dengesini destekler.
        • Vücut duruşunuzu iyileştirir.

        Değerlendirme Aralıkları:
        • < 0.90  : 🟡 Geliştirilebilir – Göğüs kasları belden daha az gelişmiş olabilir.
        • 0.90–1.10 : 🟢 Dengeli – Gövdeniz orantılı ve sağlıklı bir yapıda.
        • > 1.10  : 🌟 Güçlü gövde – Üst vücut gelişiminiz çok iyi seviyede.

        Tavsiye: Göğüs ve sırt antrenmanlarına (bench press, row, pull-up gibi) ağırlık vererek oranı artırabilir, dengeli bir vücut hattı oluşturabilirsiniz. Bel çevresini korumak da bu oranın yükselmesine katkı sağlar.
    """.trimIndent()
    ) {
        companion object {
            fun interpret(value: Float?): String = when {
                value == null -> "Veri yok."
                value < 0.9f -> "Geliştirilebilir: Göğüs hacmi artırılabilir."
                value <= 1.1f -> "Dengeli: Oranınız sağlıklı ve estetik."
                else -> "Güçlü gövde: Harika bir üst vücut yapınız var!"
            }

            fun interpretEmoji(value: Float?): String = when {
                value == null -> "❔"
                value < 0.9f -> "🟡"
                value <= 1.1f -> "🟢"
                else -> "🌟"
            }
        }
    }


    data class PushUp(
        override val score: Float?
    ) : HealthMetricResult(
        score = score,
        label = "Şınav Sayısı",
        comment = interpret(score),
        emoji = interpretEmoji(score),
        description = """
        Şınav performansı, üst vücut kuvvetinizi ve kas dayanıklılığınızı ölçen etkili bir göstergedir. Göğüs, omuz, triceps ve core kaslarınız bu harekette birlikte çalışır.

        Neden önemlidir?
        • Günlük işlerde ve sporda dayanıklılık sağlar.
        • Postürünüzü destekler.
        • Kuvvetli bir üst vücut formunun temelidir.
        • Vücut ağırlığıyla yapılan en işlevsel egzersizlerden biridir.

        Değerlendirme Aralıkları (Genel Ortalama):
        • < 10 tekrar : 🔴 Zayıf – Kuvvetlenmeye ihtiyacınız var.
        • 10–19 tekrar : 🟡 Orta – Gelişmeye açıksınız, hedefe yakınsınız.
        • ≥ 20 tekrar : 🟢 Güçlü – Üst vücut dayanıklılığınız çok iyi durumda!

        Tavsiye: Şınav sayınızı artırmak için düzenli olarak temel ve varyasyonlu şınav egzersizleri (örneğin: incline, diamond, wide grip) yapabilirsiniz. Kaslarınız gelişirken tekrar sayınız da artacaktır.
    """.trimIndent()
    ) {
        companion object {
            fun interpret(value: Float?): String = when {
                value == null -> "Veri yok."
                value < 10 -> "Zayıf: Yavaş yavaş dayanıklılığı artırabilirsiniz."
                value < 20 -> "Orta: Gelişme yolundasınız."
                else -> "Güçlü: Üst vücut performansınız çok iyi!"
            }

            fun interpretEmoji(value: Float?): String = when {
                value == null -> "❔"
                value < 10 -> "🔴"
                value < 20 -> "🟡"
                else -> "🟢"
            }
        }
    }

    data class Flexibility(
        override val score: Float?
    ) : HealthMetricResult(
        score = score,
        label = "Esneklik",
        comment = interpret(score),
        emoji = interpretEmoji(score),
        description = """
        Esneklik, kaslarınızın ve eklemlerinizin hareket açıklığını gösterir. Özellikle bel, hamstring (arka bacak) ve sırt kaslarının ne kadar elastik olduğunu ölçmek için genellikle "Sit and Reach" testi kullanılır.

        Neden önemlidir?
        • Kas gerginliğini azaltır, sakatlık riskini düşürür.
        • Duruş bozukluklarını önler.
        • Spor performansınızı destekler.
        • Günlük hareketleri daha kolay ve rahat yapmanızı sağlar.

        Değerlendirme Aralıkları:
        • < 20 cm     : 🔴 Düşük – Kaslarınız gergin olabilir, esneme çalışmaları önerilir.
        • 20 – 30 cm  : 🟡 Orta – Fena değil, gelişmeye açık bir seviye.
        • > 30 cm     : 🟢 İyi – Esnekliğiniz oldukça iyi, devam!

        Tavsiye: Esnekliğinizi artırmak için düzenli olarak dinamik ve statik esneme hareketleri (stretching), yoga veya pilates gibi disiplinleri rutininize ekleyebilirsiniz.
    """.trimIndent()
    ) {
        companion object {
            fun interpret(value: Float?): String = when {
                value == null -> "Veri yok."
                value < 20f -> "Düşük: Esneklik çalışmalarına ağırlık verilmeli."
                value <= 30f -> "Orta: Fena değil, gelişmeye açık."
                else -> "İyi: Esnekliğiniz harika seviyede!"
            }

            fun interpretEmoji(value: Float?): String = when {
                value == null -> "❔"
                value < 20f -> "🔴"
                value <= 30f -> "🟡"
                else -> "🟢"
            }
        }
    }
}

