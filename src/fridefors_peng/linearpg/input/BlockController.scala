package fridefors_peng.linearpg.input

import fridefors_peng.linearpg.Main
import fridefors_peng.linearpg.terrain.ControllableBlock
import org.newdawn.slick.{Input, MouseListener}

/**
 * abstract class providing interface for terrain that responds on user input.
 * Goes hand in hand with ControllableBlock and controlledBlocks is a list with all
 * blocks that have the same ID as this controller. If you'd like to alter every block 
 * this has control over, use controlledBlocks.foreach{ ... }
 */

abstract class BlockController(playerID:Int, val blockID:Int) extends Control(playerID) {
	
	val controlledBlocks = ControllableBlock.getBlocksWithID(blockID)
		
	def handleKeys(input: Input): Unit = {}
	def keyPressed(arg0: Int, arg1: Char): Unit = {}
	def keyReleased(arg0: Int, arg1: Char): Unit = {}
}