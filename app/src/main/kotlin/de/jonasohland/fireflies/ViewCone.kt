package de.jonasohland.fireflies

import processing.core.PApplet
import kotlin.math.*;

data class ViewCone(
    val pos: Vect2D,
    val dir: Vect2D,
    val angle: Float,
    val length: Float
)

infix fun ViewCone.drawTo(app: PApplet) {

    val lowerDir = scale(rotate(norm(dir), angle / -2), length)
    val upperDir = scale(rotate(norm(dir), angle / 2), length)

    app.noFill()
    app.fill(25F,25F,90F,0.3F)
    app.stroke(0F, 0F, 0F, 1F)

    Line(pos, lowerDir) drawTo app
    Line(pos, upperDir) drawTo app

    val dirUp = dir(upperDir);
    val dirLow = dir(lowerDir);

    if (dirUp - dirLow > 0)
        app.arc(pos.x, pos.y, length * 2, length * 2, dirLow, dirUp, PApplet.OPEN)
    else
        app.arc(pos.x, pos.y, length * 2, length * 2,  dirLow, 2 * PI.toFloat() + dirUp, PApplet.OPEN)

    val upEnd = add(pos, upperDir);
    val lowEnd = add(pos, lowerDir);

    app.noStroke()
    app.triangle(pos.x, pos.y, upEnd.x, upEnd.y, lowEnd.x, lowEnd.y)
}
