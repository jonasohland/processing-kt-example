package de.jonasohland.fireflies

import processing.core.PApplet

data class Line (
    val from: Vect2D,
    val to: Vect2D
)

infix fun Line.drawTo(app: PApplet) {
    val end = add(from, to);
    app.line(from.x, from.y, end.x, end.y)
}