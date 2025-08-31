package org.apache.harmony.awt.gl.font;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.graphics.Typeface;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class FontMetricsImpl extends FontMetrics {
    private final Paint paint;
    private int ascent;
    private int descent;
    private int leading;
    private int maxAscent;
    private int maxDescent;
    private int maxAdvance;
    private int[] widths = new int[256];
    private float scaleX = 1;
    private float scaleY = 1;
    private FontPeerImpl peer;

    public FontMetricsImpl(java.awt.Font fnt) {
        super(fnt);
        paint = convertAwtFontToAndroidPaint(fnt);
        android.graphics.Paint.FontMetrics androidMetrics = paint.getFontMetrics();

        AffineTransform at = fnt.getTransform();
        if (!at.isIdentity()) {
            scaleX = (float) at.getScaleX();
            scaleY = (float) at.getScaleY();
        }

        this.ascent = (int) Math.ceil(-androidMetrics.ascent); // Convert Android's negative ascent to positive
        this.descent = (int) Math.ceil(androidMetrics.descent); // Already positive in Android
        this.leading = (int) Math.ceil(androidMetrics.leading); // Already positive in Android

        this.maxAscent = ascent; // Set to the same as ascent in this example
        this.maxDescent = descent; // Set to the same as descent in this example
        this.maxAdvance = 0; // Not directly available in Android

        // Initialize the widths array
        char[] chars = new char[1];
        for (int i = 0; i < 256; i++) {
            chars[0] = (char) i;
            float[] charWidths = new float[1];
            paint.getTextWidths(chars, 0, 1, charWidths);
            widths[i] = (int) (charWidths[0] * scaleX);
        }
    }

    @Override
    public int getHeight() {
        return ascent + descent + leading;
    }

    @SuppressLint("WrongConstant")
    public Paint convertAwtFontToAndroidPaint(Font awtFont) {
        Paint androidPaint = new Paint();

        // Set Typeface based on AWT font name
        Typeface typeface = Typeface.create(awtFont.getName(), Typeface.NORMAL);
        androidPaint.setTypeface(typeface);

        // Set Style
        int style = awtFont.getStyle();
        int androidStyle = Typeface.NORMAL;
        if ((style & Font.BOLD) != 0) {
            androidStyle = androidStyle | Typeface.BOLD;
        }
        if ((style & Font.ITALIC) != 0) {
            androidStyle = androidStyle | Typeface.ITALIC;
        }
        androidPaint.setTypeface(Typeface.create(typeface, androidStyle));

        // Set Size
        androidPaint.setTextSize(awtFont.getSize()); // Direct conversion

        return androidPaint;
    }

    @Override
    public int getAscent() {
        return ascent;
    }

    @Override
    public int getDescent() {
        return descent;
    }

    @Override
    public int getLeading() {
        return leading;
    }

    @Override
    public int getMaxAscent() {
        return maxAscent;
    }

    @Override
    public int getMaxDescent() {
        return maxDescent;
    }

    @Override
    public int getMaxAdvance() {
        return maxAdvance;
    }

    @Override
    public int[] getWidths() {
        return widths;
    }

    @Override
    public int charWidth(char ch) {
        return (int) (paint.measureText(Character.toString(ch)) * scaleX);
    }

    /**
     * Returns the total advance width of the specified string in the metrics
     * of the Font describing this FontMetricsImpl object.
     *
     * @param str the String which width is to be measured
     * @return the total advance width of the specified string in the metrics
     * of the Font describing this FontMetricsImpl object
     */
    @Override
    public int stringWidth(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Input string must not be null.");
        }

        int width = 0;
        char chr;

        for (int i = 0; i < str.length(); i++) {
            chr = str.charAt(i);
            width += charWidth(chr);
        }

        return width;
    }

    /**
     * Returns FontPeer implementation of the Font describing this
     * FontMetricsImpl object.
     *
     * @return a FontPeer object, that is the platform dependent FontPeer
     * implementation for the Font describing this FontMetricsImpl object.
     */
    @SuppressWarnings("deprecation")
    public FontPeerImpl getFontPeer() {
        if (peer == null) {
            peer = (FontPeerImpl) font.getPeer();
        }
        return peer;
    }
}