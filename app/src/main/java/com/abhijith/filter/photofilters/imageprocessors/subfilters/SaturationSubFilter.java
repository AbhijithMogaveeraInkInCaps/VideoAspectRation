package com.abhijith.filter.photofilters.imageprocessors.subfilters;

import android.graphics.Bitmap;

import com.abhijith.filter.photofilters.imageprocessors.ImageProcessor;
import com.abhijith.filter.photofilters.imageprocessors.SubFilter;


/**
 * @author varun on 28/07/15.
 */
public class SaturationSubFilter implements SubFilter {
    private static String tag = "";

    // The Level value is float, where level = 1 has no effect on the image
    private float level;

    public SaturationSubFilter(float level) {
        this.level = level;
    }

    @Override
    public Bitmap process(Bitmap inputImage) {
        return ImageProcessor.doSaturation(inputImage, level);
    }

    @Override
    public Object getTag() {
        return tag;
    }

    @Override
    public void setTag(Object tag) {
        SaturationSubFilter.tag = (String) tag;
    }

    public void setLevel(float level) {
        this.level = level;
    }

    /**
     * Get the current saturation level
     */
    public float getSaturation() {
        return level;
    }
}
