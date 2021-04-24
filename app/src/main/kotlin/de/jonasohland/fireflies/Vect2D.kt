package de.jonasohland.fireflies

import processing.core.PApplet
import kotlin.math.*

data class Vect2D(val x: Float = 0F, val y: Float = 0F);

infix fun Vect2D.drawAsPointTo(app: PApplet) {
    app.point(this.x, this.y)
}

fun Vect2D.drawTo(app: PApplet, from: Vect2D) {
    app.line(from.x, from.y, this.x, this.y)
}

fun normAngle(angle: Float): Float {
    val a = angle % (2 * PI.toFloat());
    if (a < 0F)
        return a + 2 * PI.toFloat();
    return a;
}

fun rotate(v: Vect2D, angle: Float): Vect2D {
    return Vect2D(
        v.x * cos(angle) - v.y * sin(angle),
        v.x * sin(angle) + v.y * cos(angle)
    )
}

fun distance(v1: Vect2D, v2: Vect2D): Float {
    val dx = v2.x - v1.x;
    val dy = v2.y - v1.y;
    return sqrt(dx * dx + dy * dy)
}

val div = { v: Vect2D, scalar: Float -> Vect2D(v.x / scalar, v.y / scalar) }
val mag = { v: Vect2D -> sqrt(v.x * v.x + v.y * v.y) }
val norm = { v: Vect2D -> div(v, mag(v)) }
val scale = { v: Vect2D, sc: Float -> Vect2D(v.x * sc, v.y * sc) }
val add = { v1: Vect2D, v2: Vect2D -> Vect2D(v1.x + v2.x, v1.y + v2.y) }
val sub = { v1: Vect2D, v2: Vect2D -> Vect2D(v1.x - v2.x, v1.y - v2.y) }
val dot = { v1: Vect2D, v2: Vect2D -> v1.x * v1.y + v2.x * v2.y }
val dir = { v: Vect2D -> val vn = norm(v); atan2(vn.y, vn.x) }
val angle = { v1: Vect2D, v2: Vect2D -> acos(dot(v1, v2)) }