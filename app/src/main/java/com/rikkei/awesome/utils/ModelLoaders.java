package com.rikkei.awesome.utils;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Objects;

public class ModelLoaders implements ModelLoader<StorageReference, InputStream> {

    public static class Factory implements ModelLoaderFactory<StorageReference, InputStream>{

        @NonNull
        @Override
        public ModelLoader<StorageReference, InputStream> build(@NonNull MultiModelLoaderFactory multiFactory) {
            return new ModelLoaders();
        }

        @Override
        public void teardown() {

        }
    }

    public static class StorageKey implements Key{

        private StorageReference myRef;

        public StorageKey(StorageReference ref){
            this.myRef = ref;
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
            messageDigest.update(myRef.getPath().getBytes(Charset.defaultCharset()));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StorageKey that = (StorageKey) o;
            return myRef.equals(that.myRef);
        }

        @Override
        public int hashCode() {
            return myRef.hashCode();
        }
    }

    public static class StorageFetcher implements DataFetcher<InputStream>{

        private StorageReference myRef;
        private StreamDownloadTask myStream;
        private InputStream is;

        public StorageFetcher(StorageReference ref){
            this.myRef = ref;
        }

        @Override
        public void loadData(@NonNull Priority priority, @NonNull DataCallback<? super InputStream> callback) {
            myStream = myRef.getStream();
            myStream.addOnSuccessListener(taskSnapshot -> {
                is = taskSnapshot.getStream();
                callback.onDataReady(is);
            }).addOnFailureListener(callback::onLoadFailed);
        }

        @Override
        public void cleanup() {

            if (is != null){
                try {
                    is.close();
                    is = null;
                } catch (IOException e) {
                    Log.w("Failed", "Could not close stream", e);
                }
            }
        }

        @Override
        public void cancel() {
            if (myStream != null && myStream.isInProgress())
                myStream.cancel();
        }

        @NonNull
        @Override
        public Class<InputStream> getDataClass() {
            return InputStream.class;
        }

        @NonNull
        @Override
        public DataSource getDataSource() {
            return DataSource.REMOTE;
        }
    }

    @Nullable
    @Override
    public LoadData<InputStream> buildLoadData(@NonNull StorageReference storageReference, int width, int height, @NonNull Options options) {
        return new LoadData<>(new StorageKey(storageReference), new StorageFetcher(storageReference));
    }

    @Override
    public boolean handles(@NonNull StorageReference storageReference) {
        return true;
    }
}
