import ij.IJ
import net.imagej.legacy.IJ1Helper
import net.imglib2.img.display.imagej.ImageJFunctions
import org.scijava.display.DisplayService
import org.scijava.io.IOService
import org.scijava.log.LogService

// Services
io = IJ1Helper.getLegacyContext().getService(IOService.class)
disp = IJ1Helper.getLegacyContext().getService(DisplayService.class)
log = IJ1Helper.getLegacyContext().getService(LogService.class)


// Parameter
repurl = "http://imagej.net/images/"


// ij1 variant 1
ij11 = IJ.openImage(repurl + 'blobs.gif')
ij11.setTitle('IJ.openImage()')
ij11.show()
log.info('IJ.openImage() -> ' + ij11.getClass())

// ij1 variant 2 (open display, then grab the current display ij1-macro style)
IJ.open("${repurl}blobs.gif")
ij12 = IJ.getImage()
ij12.setTitle('IJ.open()')
log.info('IJ.open() -> ' + ij12.getClass())

// ij2 service
ds = io.open("${repurl}blobs.gif")
disp.createDisplay('dataset', ds)
log.info('ij.io().open() -> ' + ds.getClass())

// switch from ij2 to ij1 world
wra1 = ImageJFunctions.wrap(ds.getImgPlus().getImg(), 'wrapped dataset')
wra1.show()
log.info('ImageJFunctions.wrap(img, title) -> ' + wra1.getClass())

// switch from ij1 to ij2 world
wra2 = ImageJFunctions.wrap(ij11)
log.info('ImageJFunctions.wrap(imp) -> ' + wra2.getClass())
