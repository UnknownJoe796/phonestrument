package com.ivieleague.phonestrument

public object Pitch {
    val C0 = 16.35f
    val Cs0 = 17.32f
    val D0 = 18.35f
    val Ds0 = 19.45f
    val E0 = 20.60f
    val F0 = 21.83f
    val Fs0 = 23.12f
    val G0 = 24.50f
    val Gs0 = 25.96f
    val A0 = 27.50f
    val As0 = 29.14f
    val B0 = 30.87f
    val C1 = 32.70f
    val Cs1 = 34.65f
    val D1 = 36.71f
    val Ds1 = 38.89f
    val E1 = 41.20f
    val F1 = 43.65f
    val Fs1 = 46.25f
    val G1 = 49.00f
    val Gs1 = 51.91f
    val A1 = 55.00f
    val As1 = 58.27f
    val B1 = 61.74f
    val C2 = 65.41f
    val Cs2 = 69.30f
    val D2 = 73.42f
    val Ds2 = 77.78f
    val E2 = 82.41f
    val F2 = 87.31f
    val Fs2 = 92.50f
    val G2 = 98.00f
    val Gs2 = 103.83f
    val A2 = 110.00f
    val As2 = 116.54f
    val B2 = 123.47f
    val C3 = 130.81f
    val Cs3 = 138.59f
    val D3 = 146.83f
    val Ds3 = 155.56f
    val E3 = 164.81f
    val F3 = 174.61f
    val Fs3 = 185.00f
    val G3 = 196.00f
    val Gs3 = 207.65f
    val A3 = 220.00f
    val As3 = 233.08f
    val B3 = 246.94f
    val C4 = 261.63f
    val Cs4 = 277.18f
    val D4 = 293.66f
    val Ds4 = 311.13f
    val E4 = 329.63f
    val F4 = 349.23f
    val Fs4 = 369.99f
    val G4 = 392.00f
    val Gs4 = 415.30f
    val A4 = 440.00f
    val As4 = 466.16f
    val B4 = 493.88f
    val C5 = 523.25f
    val Cs5 = 554.37f
    val D5 = 587.33f
    val Ds5 = 622.25f
    val E5 = 659.25f
    val F5 = 698.46f
    val Fs5 = 739.99f
    val G5 = 783.99f
    val Gs5 = 830.61f
    val A5 = 880.00f
    val As5 = 932.33f
    val B5 = 987.77f
    val C6 = 1046.50f
    val Cs6 = 1108.73f
    val D6 = 1174.66f
    val Ds6 = 1244.51f
    val E6 = 1318.51f
    val F6 = 1396.91f
    val Fs6 = 1479.98f
    val G6 = 1567.98f
    val Gs6 = 1661.22f
    val A6 = 1760.00f
    val As6 = 1864.66f
    val B6 = 1975.53f
    val C7 = 2093.00f
    val Cs7 = 2217.46f
    val D7 = 2349.32f
    val Ds7 = 2489.02f
    val E7 = 2637.02f
    val F7 = 2793.83f
    val Fs7 = 2959.96f
    val G7 = 3135.96f
    val Gs7 = 3322.44f
    val A7 = 3520.00f
    val As7 = 3729.31f
    val B7 = 3951.07f
    val C8 = 4186.01f
    val Cs8 = 4434.92f
    val D8 = 4698.63f
    val Ds8 = 4978.03f
    val E8 = 5274.04f
    val F8 = 5587.65f
    val Fs8 = 5919.91f
    val G8 = 6271.93f
    val Gs8 = 6644.88f
    val A8 = 7040.00f
    val As8 = 7458.62f
    val B8 = 7902.13f

    val twelfthRootOfTwo = Math.pow(2.0, 1 / 12.0)
    fun transpose(input: Float, steps: Int): Float {
        return (input * Math.pow(twelfthRootOfTwo, steps.toDouble())).toFloat()
    }
}

fun Float.transpose(steps: Int): Float {
    return (this * Math.pow(Pitch.twelfthRootOfTwo, steps.toDouble())).toFloat()
}