package lolirofle.data

trait OneDimensionalAxis
trait TwoDimensionalAxis extends OneDimensionalAxis
trait ThreeDimensionalAxis extends TwoDimensionalAxis

object Axis{
	case object X extends OneDimensionalAxis
	case object Y extends TwoDimensionalAxis
	case object Z extends ThreeDimensionalAxis
}