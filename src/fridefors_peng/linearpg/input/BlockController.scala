package fridefors_peng.linearpg.input

import fridefors_peng.linearpg.terrain.ControllableBlock

/**
 * abstract class providing interface for terrain that responds on user input.
 * Goes hand in hand with ControllableBlock and controlledBlocks is a list with all
 * blocks that have the same ID as this controller. If you'd like to alter every block 
 * this has control over, use controlledBlocks.foreach{ ... }
 */

abstract class BlockController(playerID:Int, val blockID:Int) extends Control{
	val controlledBlocks = ControllableBlock.getBlocksWithID(blockID)
}