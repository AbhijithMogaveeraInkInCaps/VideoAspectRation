package com.abhijith.filtercombination

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.core.net.toFile
import com.abhijith.photofilters.SampleFilters
import com.abhijith.photofilters.geometry.Point
import com.abhijith.photofilters.imageprocessors.Filter
import com.abhijith.photofilters.imageprocessors.subfilters.*
import com.abhijith.videoaspectration.R
import com.abhijith.videoaspectration.convertScale
import com.abhijith.videofilters.filter.*

class FilterList(val context: Context) {
    fun getFilter(filterEnum: FilterEnum): CamFilter {

        when (filterEnum) {

            FilterEnum.BRIGHTNESS_FILTER -> {
                return getBrightnessFilter()
            }

            FilterEnum.CONTRAST_FILTER -> {
                return getContrastFilter()
            }

            FilterEnum.SATURATION_FILTER -> {
                return getSaturationFilter()
            }

//            FilterEnum.TONE_CURVE_FILTER -> {
//                https://stackoverflow.com/questions/20995115/how-use-acv-a-file-for-tone-curve-in-gpuimage-in-android/33560421
//                https://www.google.com/search?q=.acv+file&rlz=1C1CHBD_enIN923IN923&oq=.acv&aqs=chrome.1.69i57j0j0i30l8.2866j0j7&sourceid=chrome&ie=UTF-8
//                https://vimeo.com/110614109
//
//                val myFilter = Filter()
//                val rgbKnots: Array<Point?> = arrayOfNulls(3)
//                rgbKnots[0] = Point(255f, 255F)
//                rgbKnots[1] = Point(255f, 255f)
//                rgbKnots[0] = Point(39f,173f )
//                myFilter.addSubFilter(ToneCurveSubFilter( null,null, null, rgbKnots))
//                return CamFilter(SampleFilters.getAweStruckVibeFilter(),GlFilterGroup(GlRGBFilter().apply {
//                return CamFilter(myFilter,GlFilterGroup(GlRGBFilter().apply {
//                    this.setBlue(12f)
//                }))
//            }

            FilterEnum.VIGNETTE_FILTER -> {
                return getVignetteFilter()
            }
        }
    }

    private fun getVignetteFilter(): CamFilter {
        val imageFilter = Filter().apply {
            addSubFilter(ColorOverlaySubFilter(255, convertScale(55),convertScale(87),convertScale(87)))
            addSubFilter(BrightnessSubFilter(-10))
        }
        val videoFilter = GlFilterGroup(GlVignetteFilter().apply {
            this.vignetteStart = 10f
            this.vignetteEnd = 10f
        })
        return CamFilter(imageFilter, videoFilter)
    }

    private fun getBrightnessFilter(): CamFilter {
        val imageFilter = Filter().apply {
//            addSubFilter(ColorOverlaySubFilter(255, convertScale(150),convertScale(131),convertScale(114)))
            addSubFilter(ColorOverlaySubFilter(255, convertScale(150), convertScale(131),
                convertScale(114)))
        }
        val videoFilter = GlFilterGroup(GlBrightnessFilter().apply {
            setBrightness(10f)
        })
        return CamFilter(imageFilter, videoFilter)
    }

    private fun getContrastFilter(): CamFilter {
        val imageFilter = Filter().apply {
            addSubFilter(ColorOverlaySubFilter(255, convertScale(114),convertScale(97),convertScale(143)))
//            addSubFilter(ColorOverlaySubFilter(1,114f,97f,143f))
        }
        val videoFilter = GlFilterGroup(GlContrastFilter().apply {
            setContrast(30f)
        })
        return CamFilter(imageFilter, videoFilter)
    }

    private fun getSaturationFilter(): CamFilter {
        val imageFilter = Filter().apply {
            addSubFilter(ColorOverlaySubFilter(255, convertScale(103),convertScale(143),convertScale(117)))
//            addSubFilter(ColorOverlaySubFilter(1,103f,143f,117f))
        }
        val videoFilter = GlFilterGroup(GlSaturationFilter().apply {
            setSaturation(30f)
        })
        return CamFilter(imageFilter, videoFilter)
    }
}

class CamFilter(val imageFilter: Filter, val videoFilter: GlFilterGroup)
enum class FilterEnum {
    BRIGHTNESS_FILTER, CONTRAST_FILTER, SATURATION_FILTER, VIGNETTE_FILTER//, TONE_CURVE_FILTER
}
fun Context.resourceUri(resourceId: Int): Uri = with(resources) {
    Uri.Builder()
        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(getResourcePackageName(resourceId))
        .appendPath(getResourceTypeName(resourceId))
        .appendPath(getResourceEntryName(resourceId))
        .build()
}

/*
* Gngham cce3f1
* moon #f4f1c9
*
*
*
* */