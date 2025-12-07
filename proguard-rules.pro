# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Retrofit
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

-keep @retrofit2.http.* class *

# OkHttp
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

# Okio
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**

# Gson
-keep class com.google.gson.** { *; }
-keep class com.example.osclassapp.models.** { *; }
-keep class com.example.osclassapp.api.responses.** { *; }
-keep class com.example.osclassapp.api.requests.** { *; }

# For DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

# Material Components
-keep class com.google.android.material.** { *; }
-keep interface com.google.android.material.** { *; }

# AndroidX
-keep class androidx.** { *; }
-keep interface androidx.** { *; }

# Application classes
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

# Model classes used in JSON serialization/deserialization
-keepclassmembers class com.example.osclassapp.models.** {
    public <methods>;
    public <fields>;
}

# API response classes
-keepclassmembers class com.example.osclassapp.api.responses.** {
    public <methods>;
    public <fields>;
}

# Request classes
-keepclassmembers class com.example.osclassapp.api.requests.** {
    public <methods>;
    public <fields>;
}

# Remove logging in release builds
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

# Keep generic signatures for better reflection support
-keepattributes Signature

# Keep annotations for better compatibility
-keepattributes *Annotation*

# Keep which classes may have native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep custom view constructors
-keepclassmembers public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(***);
}

# Keep parcelable classes
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep serializable classes
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Keep R classes
-keep class **.R$* {
    <fields>;
}

# Для Kotlin
-keep class kotlin.** { *; }
-dontwarn kotlin.**
-keep class org.jetbrains.kotlin.** { *; }
-dontwarn org.jetbrains.kotlin.**

# Для AndroidX Startup
-dontwarn androidx.startup.**

# Keep the support library references
-dontwarn android.support.**
-dontwarn com.google.android.material.**

# For Google Play Services
-dontwarn com.google.android.gms.**