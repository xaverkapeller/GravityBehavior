# GravityBehavior

A `CoordinatorLayout.Behavior` which aligns a `View` according to gravity.

## Installation

To use the GravityBehavior just add this dependency to your build.gradle:

```
compile 'com.github.wrdlbrnft:gravity-behavior:0.1.0.4'
```

If you are using maven you can add it like this: 

```
<dependency>
  <groupId>com.github.wrdlbrnft</groupId>
  <artifactId>gravity-behavior</artifactId>
  <version>0.1.0.4</version>
</dependency>
```

---

## How to use it

You can add the `Behavior` to any `View` which is a direct child of a `CoordinatorLayout`. The library provides a string resource called `gravity_behavior` to simplify this:

```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.design.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        android:id="@+id/text"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:text="@string/some_text"
        app:layout_behavior="@string/gravity_behavior"/>

</android.support.design.widget.CoordinatorLayout>
```

After that you can get the `GravityBehavior` instance for a `View` by calling `GravityBehavior.of()`:

```java
private GravityBehavior mGravityBehavior;

@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final View view = findViewById(R.id.text);
    mGravityBehavior = GravityBehavior.of(view);
}
```

With the `GravityBehavior` instance you can start and stop rotating the `View` by calling `start()` and `stop()`. To conserve battery and avoid memory leaks you should always stop the behavior when the `View` is not visible. In most cases it makes sense to simply call `start()` in `onResume()` and `stop()` in `onPause()`:

```java
@Override
protected void onResume() {
    super.onResume();
    mGravityBehavior.start();
}

@Override
protected void onPause() {
    super.onPause();
    mGravityBehavior.stop();
}
```

And that's everything you have to do to get `GravityBehavior` to work.

---

## Additional Options

### Smoothing Factor

GravityBehavior internally smooths out sensor values with a very simple filter. The amount of smoothing can be controlled with a smoothing factor which can be modified with a setter:

```java
mGravityBehavior.setSmoothingFactor(8.0f);
```

The default smoothing factor is `4.0f` (there is also a public constant in the `GravityBehavior` for that). Increasing the smoothing factor will make the rotation of the `View` smoother, but it will also react more slowly to changes in the orientation of the device. `4.0f` should be the optimal compromise of smoothness and reaction speed for most devices, but you can play around with it a little if you want.

### Base Rotation

GravityBehavior can apply a base rotation to the `View`. The `View` will always be rotated by the base rotation in addition to the rotation applied by GravityBehavior. The `GravityBehavior` exposes a setter for the base rotation:

```java
mGravityBehavior.setBaseRotation(90.0f);
```

And `GravityBehavior` also exposes a `Property` for use in an `Animator` so you can easily animate the base rotation value:

```java
final Animator animator = ObjectAnimator.ofFloat(view, GravityBehavior.BASE_ROTATION, 0.0f, 90.0f);
animator.start();
```
