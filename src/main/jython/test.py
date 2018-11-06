# This is not indexed by intellij
import net.imagej.legacy.IJ1Helper
import org.scijava.log.LogService

from net.imglib2.img.display.imagej import ImageJFunctions
# log = IJ1Helper.getLegacyContext().getService(LogService.class)

# Finds methods but no signatures
from ij import IJ
IJ.open()


repurl = "http://imagej.net/images"

IJ.open(repurl + "/FluorescentCells.jpg")
imp = IJ.getImage()
print imp.getClass()

img = ImageJFunctions.wrap(imp)
print img.getClass()

