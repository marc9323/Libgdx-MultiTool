package com.staticvoid.common;

import com.staticvoid.ActionsSample;
import com.staticvoid.ApplicationListenerSample;
import com.staticvoid.AshleyEngineSample;
import com.staticvoid.AshleySystemSample;
import com.staticvoid.AssetManagerSample;
import com.staticvoid.BitmapFontSample;
import com.staticvoid.CustomActorSample;
import com.staticvoid.GdxGeneratedSample;
import com.staticvoid.GdxModuleInfoSample;
import com.staticvoid.GdxReflectionSample;
import com.staticvoid.InputListeningSample;
import com.staticvoid.InputPollingSample;
import com.staticvoid.MultiTool;
import com.staticvoid.OrthographicCameraSample;
import com.staticvoid.PerlinNoiseSample;
import com.staticvoid.PieMenuSample;
import com.staticvoid.PoolingSample;
import com.staticvoid.ShapeRendererSample;
import com.staticvoid.SkinSample;
import com.staticvoid.SpriteBatchSample;
import com.staticvoid.TableSample;
import com.staticvoid.TextureAtlasSample;
import com.staticvoid.ViewportSample;
import com.staticvoid.cookbook.AnimatedSpriteCookbook;
import com.staticvoid.cookbook.EnvironmentTest;
import com.staticvoid.cookbook.OrthographicCameraSampleCookbook;
import com.staticvoid.cookbook.SpriteBatchSampleCookbook;
import com.staticvoid.cookbook.SpriteSampleCookbook;
import com.staticvoid.cookbook.TextureAtlasCookbook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SampleInfos {

    // TODO:  Would it be possible for a method here to inspect the directory and load this list automagickally?
    public static final List<SampleInfo> ALL = Arrays.asList(
            ApplicationListenerSample.SAMPLE_INFO,
            GdxGeneratedSample.SAMPLE_INFO,
            GdxModuleInfoSample.SAMPLE_INFO,
            GdxReflectionSample.SAMPLE_INFO,
            InputListeningSample.SAMPLE_INFO,
            InputPollingSample.SAMPLE_INFO,
            MultiTool.SAMPLE_INFO,
            OrthographicCameraSample.SAMPLE_INFO,
            ViewportSample.SAMPLE_INFO,
            SpriteBatchSample.SAMPLE_INFO,
            ShapeRendererSample.SAMPLE_INFO,
            BitmapFontSample.SAMPLE_INFO,
            PoolingSample.SAMPLE_INFO,
            AssetManagerSample.SAMPLE_INFO,
            TextureAtlasSample.SAMPLE_INFO,
            CustomActorSample.SAMPLE_INFO,
            ActionsSample.SAMPLE_INFO,
            TableSample.SAMPLE_INFO,
            SkinSample.SAMPLE_INFO,
            PieMenuSample.SAMPLE_INFO,
            AshleyEngineSample.SAMPLE_INFO,
            AshleySystemSample.SAMPLE_INFO,
            PerlinNoiseSample.SAMPLE_INFO, // cookbook samples
            EnvironmentTest.SAMPLE_INFO,
            SpriteBatchSampleCookbook.SAMPLE_INFO,
            TextureAtlasCookbook.SAMPLE_INFO,
            SpriteSampleCookbook.SAMPLE_INFO,
            AnimatedSpriteCookbook.SAMPLE_INFO,
            OrthographicCameraSampleCookbook.SAMPLE_INFO
    );

    public static List<String> getSampleNames() {
        List<String> ret = new ArrayList<>();

        for (SampleInfo info : ALL) {
            ret.add(info.getName());
        }

        Collections.sort(ret);
        return ret;
    }

    public static SampleInfo find(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name argument is required.");
        }

        SampleInfo ret = null;

        for (SampleInfo info : ALL) {
            // TODO:  IMPORTANT!  equals for strings
            // there could be multiple references to 'the same' string
            // == operator compares references not string content
            // equals method compares objects for equality in case of strings that is content
            if (info.getName().equals(name)) {
                ret = info;
                break;
            }
        }

        if (ret == null) {
            throw new IllegalArgumentException("Could not find sample with name= " + name);
        }

        return ret;
    }

    // private constructor
    private SampleInfos() {
    }
}
