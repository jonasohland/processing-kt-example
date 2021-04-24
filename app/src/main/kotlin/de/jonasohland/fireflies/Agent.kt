package de.jonasohland.fireflies

import processing.core.PApplet

data class Agent(
    val pos: Vect2D,
    val acc: Vect2D = Vect2D(),
    val blinkCycle: Float
);

infix fun Agent.sees(ag: Agent): Boolean {
    return mag(sub(ag.pos, pos)) < 100F;
}

fun wrapVal(value: Float, min: Float, range: Float): Float {
    return (((value - min) % range) + range) % range + min;
}

fun translateWrapped(pos: Vect2D, trans: Vect2D, bounds: Rect): Vect2D {
    return Vect2D(
        wrapVal(pos.x + trans.x, bounds.x, bounds.x + bounds.width),
        wrapVal(pos.y + trans.y, bounds.y, bounds.y + bounds.height)
    )
}

fun move(agent: Agent, bounds: Rect): Agent {
    return Agent(
        translateWrapped(agent.pos, agent.acc, bounds),
        agent.acc,
        agent.blinkCycle
    );
}

fun sync(agent: Agent, agents: List<Agent>, app: PApplet): Agent {

    val diff = agents.filter {
        it sees agent
    }
    .map {
        val lr = it.blinkCycle - agent.blinkCycle
        val rl = agent.blinkCycle - it.blinkCycle
        if (wrapVal(lr, 0F, 50F) > wrapVal(rl, 0F, 50F))
            rl / 5000
        else
            lr / 5000
    }
    .ifEmpty { listOf(0F) }
    .average().toFloat()

    return Agent(agent.pos, agent.acc, wrapVal(agent.blinkCycle + diff, 0F, 50F))
}

fun randomizeDirection(ag: Agent, factor: Float = 0.2F): Agent {
    return Agent(
        ag.pos,
        rotate(ag.acc, (Math.random() * factor).toFloat() - factor / 2),
        ag.blinkCycle
    )
}

infix fun Agent.drawTo(app: App): Agent {

    if (blinkCycle < app.blinkCycle + 5 && blinkCycle > app.blinkCycle)
        app.fill(255F, 255F, 0F, 1F )
    else
        app.fill(10F, 10F, 10F, 1F )

    app.ellipse(this.pos.x, this.pos.y, 15F, 15F)
    return this;
}