package org.muzi.open.helper.test;

import com.intellij.openapi.components.BaseComponent;
import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.messages.MessageBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.SystemIndependent;
import org.muzi.open.helper.ui.IndexUI;
import org.picocontainer.PicoContainer;

/**
 * @author: muzi
 * @time: 2019-06-08 22:24
 * @description:
 */
public class TestUI {
    //修改 org.muzi.open.helper.config.BaseConf.Conf.DEBUG 为true，便于测试
    public static void main(String[] args) {
        new IndexUI(new Project() {
            @NotNull
            @Override
            public String getName() {
                return null;
            }

            @Override
            public VirtualFile getBaseDir() {
                return null;
            }

            @Nullable
            @Override
            public @SystemIndependent String getBasePath() {
                return null;
            }

            @Nullable
            @Override
            public VirtualFile getProjectFile() {
                return null;
            }

            @Nullable
            @Override
            public @SystemIndependent String getProjectFilePath() {
                return null;
            }

            @Nullable
            @Override
            public VirtualFile getWorkspaceFile() {
                return null;
            }

            @NotNull
            @Override
            public String getLocationHash() {
                return null;
            }

            @Override
            public void save() {

            }

            @Override
            public boolean isOpen() {
                return false;
            }

            @Override
            public boolean isInitialized() {
                return false;
            }

            @Override
            public boolean isDefault() {
                return false;
            }

            @Override
            public BaseComponent getComponent(@NotNull String s) {
                return null;
            }

            @Override
            public <T> T getComponent(@NotNull Class<T> aClass) {
                return null;
            }

            @Override
            public <T> T getComponent(@NotNull Class<T> aClass, T t) {
                return null;
            }

            @Override
            public boolean hasComponent(@NotNull Class aClass) {
                return false;
            }

            @NotNull
            @Override
            public <T> T[] getComponents(@NotNull Class<T> aClass) {
                return null;
            }

            @NotNull
            @Override
            public PicoContainer getPicoContainer() {
                return null;
            }

            @NotNull
            @Override
            public MessageBus getMessageBus() {
                return null;
            }

            @Override
            public boolean isDisposed() {
                return false;
            }

            @NotNull
            @Override
            public <T> T[] getExtensions(@NotNull ExtensionPointName<T> extensionPointName) {
                return null;
            }

            @NotNull
            @Override
            public Condition<?> getDisposed() {
                return null;
            }

            @Override
            public void dispose() {

            }

            @Nullable
            @Override
            public <T> T getUserData(@NotNull Key<T> key) {
                return null;
            }

            @Override
            public <T> void putUserData(@NotNull Key<T> key, @Nullable T t) {

            }
        }).show(200, 200);
    }
}
