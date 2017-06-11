package com.angcyo.sharebook.control;

import android.os.Environment;
import android.view.View;

import com.angcyo.library.utils.L;
import com.angcyo.uiview.RApplication;
import com.angcyo.uiview.Root;
import com.angcyo.uiview.container.ILayout;
import com.angcyo.uiview.dialog.UIDialog;
import com.angcyo.uiview.github.utilcode.utils.AppUtils;
import com.angcyo.uiview.github.utilcode.utils.FileUtils;
import com.liulishuo.FDown;
import com.liulishuo.FDownListener;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.SimpleTask;

import java.io.File;

/**
 * Created by angcyo on 2017-05-29.
 */

public class UpdateHelper {
    public static void showUpdateDialog(ILayout layout,
                                        String versionName, String des, final String downloadUrl,
                                        boolean isForceUpdate) {

        String targetPath = generateApkFilePath(versionName + ".apk");
        final File targetFile = new File(targetPath);

        //版本检测
        UIDialog.build()
                .setDialogTitle("发现新版本:" + versionName)
                .setDialogContent(des)
                .setOkText(
                        targetFile.exists() ? "安装" : "立即下载"
                )
                .setCancelText(isForceUpdate ? "" : "下次再说")
                .setCancelClick(new UIDialog.OnDialogClick() {
                    @Override
                    public void onDialogClick(UIDialog dialog, View clickView) {
                        dialog.setAutoFinishDialog(true);
                    }
                })
                .setOkClick(new UIDialog.OnDialogClick() {
                    @Override
                    public void onDialogClick(final UIDialog dialog, View clickView) {
                        dialog.setAutoFinishDialog(false);

                        if (targetFile.exists()) {
                            AppUtils.installApp(RApplication.getApp(), targetFile);
                        } else {
                            dialog.setCancelText("");
                            dialog.setOkText("下载中...");
                            downloadFile(downloadUrl, targetFile, new FDownListener() {
                                @Override
                                public void onCompleted(BaseDownloadTask task) {
                                    super.onCompleted(task);
                                    dialog.setOkText("安装");
                                    AppUtils.installApp(RApplication.getApp(), targetFile);
                                }

                                @Override
                                public void onProgress(BaseDownloadTask task, int soFarBytes, int totalBytes, float progress) {
                                    super.onProgress(task, soFarBytes, totalBytes, progress);
                                    dialog.setIncertitudeProgress(totalBytes == -1);
                                    dialog.setProgress((int) progress);

                                    //L.d("下载进度:" + task.getUrl() + ":" + VersionControl.INSTANCE.getTargetFile().getAbsolutePath() + " -> total:" + totalBytes + " :" + progress);

//                                    ProgressNotify.instance()
//                                            .setClickActivity(HnUIMainActivity.class)
//                                            .setTargetFilePath(VersionControl.INSTANCE.getTargetFile().getAbsolutePath())
//                                            .show("恐龙谷", R.drawable.logo, (int) progress);
                                }
                            });
                        }
                    }
                })
                .setCanCanceledOnOutside(false)
                .setCanCancel(!isForceUpdate)
                .showDialog(layout);
    }


    private static String generateApkFilePath(String name) {
        String musicFolder = Environment.getExternalStorageDirectory().getAbsolutePath() +
                File.separator +
                Root.APP_FOLDER +
                File.separator +
                "apk";
        new File(musicFolder).mkdirs();
        return musicFolder + File.separator + name;
    }

    private static void downloadFile(String downUrl, final File targetFile, final FDownListener listener) {
        if (targetFile.exists()) {
            SimpleTask task = new SimpleTask();
            task.setPath(targetFile.getAbsolutePath());
            listener.onCompleted(task);
        } else {
            final String tempPath = targetFile.getAbsolutePath() + ".temp";
            L.e("下载:" + downUrl + " 至:" + tempPath);
            FDown.build(downUrl)
                    .setFullPath(tempPath)
                    .download(new FDownListener() {
                        @Override
                        public void onCompleted(BaseDownloadTask task) {
                            super.onCompleted(task);
                            FileUtils.rename(new File(task.getPath()), targetFile.getName());
                            task.setPath(targetFile.getAbsolutePath());
                            listener.onCompleted(task);
                        }

                        @Override
                        public void onProgress(BaseDownloadTask task, int soFarBytes, int totalBytes, float progress) {
                            super.onProgress(task, soFarBytes, totalBytes, progress);
                            listener.onProgress(task, soFarBytes, totalBytes, progress);
                        }

                        @Override
                        public void onError(BaseDownloadTask task, Throwable e) {
                            super.onError(task, e);
                            listener.onError(task, e);
                        }
                    });
        }
    }
}
