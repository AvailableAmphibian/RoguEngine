package util.helper

import map.Stage
import util.factories.StageBuilder

/**
 * Singleton used to create Stages and having the current stage anywhere.
 */
object StageHelper {
    val stageBuilder by lazy { StageBuilder() }
    lateinit var currentStage: Stage
}
