package cn.mapview

import android.content.Context
import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream


/**
 * @author
 * data: 2024/5/28 12:33
 * desc:
 */
object SvgManager {

    val hcNames = arrayOf(
        "平谷区",
        "密云县",
        "门头沟区",
        "昌平区",
        "延庆县",
        "怀柔区",
        "顺义区",
        "房山区",
        "通州区",
        "北京市区",
        "大兴区",
    )
    val hcSvgPaths = arrayOf(
        "M611,256.8c0.4,3.1,1.7,6.1,3.4,9c5.9,10.1,12.3,19.9,18.5,29.9   c4.2,6.7,11.2,9.1,18.3,11.9c0,4.9,0,10,0,15.7c-5.2-0.9-7.5,2-9.3,6.1c-3.2,7.1-10.2,10.5-15.9,14.9c-3.3,2.6-8.7,2.7-13.3,3.6   c-3.7,0.7-6.6,2.3-9,5.1c-4.7,5.7-11,8.2-18,7.2c-12.7-2-21.9,4.1-31,11.4c-0.6,0.5-1.4,0.9-2.3,1.5c-3.2-1.1-8.7-2.9-11.8-3.9l0,0   c5-4,5.6-11.1,1.1-16.3c-1.7-1.9-3.6-4.4-3.8-6.7c-0.6-5.6-1.2-11.5,0.1-16.8c1.3-5,0.1-8.2-3.1-11.3l-1.1-1.1   c-0.2-3-0.4-7.1-0.6-10.1l0,0.1c0.7,0.2,1.4,0.3,2.1,0.4c2.5-0.4,5.3-0.9,7.9-1.4c-0.7-3.2-1.4-6.2-2.2-9.7   c4.4-4.7,7.3-10.5,6.9-17.5c-0.3-5.1,1.9-7.9,6.7-9.7c7.1-2.6,13.8-6.1,20.9-8.8c7.9-3.1,15.5-7.8,24.8-4.6   C603.3,256.7,607.9,256.4,611,256.8",
        "M611,256.8c-3.2-0.4-7.8-0.1-10.7-1.1c-9.2-3.1-16.8,1.6-24.8,4.6   c-7,2.7-13.8,6.3-20.9,8.8c-4.9,1.8-7.1,4.6-6.7,9.7c0.5,7-2.4,12.8-6.9,17.5c0.8,3.5,1.5,6.5,2.2,9.7c-2.6,0.5-5.4,1-7.9,1.4   c-7.9-1.2-12.2-5.9-15.1-12.8c-1.6-3.7-4.6-6.8-7.5-10.9c-3.7,1.5-6.8,2.8-9.8,4.1c-0.6,0.3-1.3,1.2-1.7,1   c-7.6-2.2-14.2,2.8-21.4,2.8c-0.6,0-1.8-2.1-2.4-2.7c0.2-0.1,0.4-0.2,0.6-0.3c3.3-1.5,3.8-6.8,1.6-9.7c-4.2-5.7-6.3-10.4-4.2-18.2   c2.5-9.9-0.5-19.9-7.9-28.1c-2.7-3-5.3-6.7-3.9-11.1c1-3,2.6-5.9,4.5-8.4c2.6-3.3,2.8-6.3,0.5-9.6c-0.8-1.2-1.3-2.5-2-3.8   c0.5,0,1,0,1.5,0.1c0.7-2.9,1.9-5.7,2.1-8.7c0.6-7.9,0.7-15.7,1.2-23.6c0.9-12.1,7-19.2,19.3-19.2c1.2-2.4,1.9-4.4,3.2-6   c4.8-6.1,13.1-8.5,16.9-15.8c0.3-0.6,1.4-0.8,2.2-1.1c2.8-1.1,6.6-2.7,9.5-3.8c4.8,6.2,9.3,12.8,14.2,19   c5.3,6.6,11.6,12.4,20.7,12.3c6.6-0.1,13.1-2,19.7-1.9c7.1,0.1,14.4,1,21.3,2.8c8.6,2.3,16.8,6.1,25.4,8.8c7.9,2.5,15.4,1.5,23.1-2   c8.8-4.1,18.5-5.1,28-3.3c4.2,0.8,8.1,3.5,11.7,5.2c1.1,7.9-0.3,9.3-7.2,7.5c-4.2-1.1-8.9-0.8-10.9,2.5c-1.6,2.8-0.7,7.3-0.2,11   c0.4,2.6,1.9,5,3.1,7.9c-5.8,4.9-13.9,2.5-21,4.7c-10.4-8-26.2-4.7-31.9,7.5c1,2.3,2.1,4.9,3.4,7.8c-3.2,5.9-13.5-0.7-15.6,10.1   c1.5,3.1,3.8,7.9,6.2,12.6c2.2,4.3,2.9,8.7,0.5,12.9C611.1,250.2,610.6,253.5,611,256.8",
        "M284.8,354.8c0,0.2,0,0.3,0,0.5c0.2,2.8,0.2,5.8,1.2,8.4   c2,5.2,5.6,8.7,11.7,9.1c5.1,0.4,9.7,2.4,13.5,6.1c2.4,2.3,4.3,4.6,3,8.5c-0.5,1.4,0.6,3.3,1.1,5.2c6.9,3.3,10.8,8.5,10.1,16.6   c-0.3,3.5,2.2,4.2,5,3.9c1.7-0.2,3.4-0.9,5.1-1.1c6.6-0.6,8.3,1.4,7.3,8.4c-8.6,5.8-12.6,5.6-20.2-1.1c-2.4,0-4.9-0.1-7.3,0   c-2.4,0.1-4.9,0.3-8.2,0.6c-0.4,4.4-1,9.4-1.4,13.9l0,0c-0.7-0.6-1.4-1.1-2.2-1.5c-5.9-3.4-11.9-6.6-18.9-10.4   c-9.4,1.8-15.5-1.5-22.4-13.7c-6,4.4-11.8,2.1-17.1-0.6c-4.3,1.2-8,2.7-11.9,3.2c-3.6,0.5-6.6-1-9.1-4.3c-4.7-6.4-8.4-7-16.2-4   c-8.6,3.3-17.2,6.5-25.9,9.5c-8.5,3-14,8.8-17.4,16.9c-0.3,0.8-0.9,1.5-1.1,2.3c-1.8,6.1-7.7,6.7-12.3,9.2l0,0   c-1.1-1.3-2.6-5.1-4-5.7c-7.8-3.4-8.7-10.4-9.6-17.5c-0.5-4-0.9-8.1-2.3-11.8c-1.9-4.9-6.2-7.6-11.1-9   c-13.2-3.8-12.9-11.5-7.9-21.4c5.1-10,13.8-16.5,23.1-21.8c14.4-8.2,28.8-16.7,45.5-19.8c2.8-0.5,5.6-0.8,8.4-1.1   c4.2-0.6,8.3-1.1,11.4-1.5c2.8-6.3,5-11.1,7.7-17c3.2,3.6,5.2,6.1,7.4,8.4c3.8,4,7.3,7.9,13.4,7.8c0.7,0,1.5-0.1,2.3-0.2   c5.1-0.9,9.4,3.6,11.6,7.7C255.2,352.6,269.3,355.8,284.8,354.8",
        "M263,284.8c2.5-4.7,5.9-9.7,8.8-15.1c2.1,1.2,4.5,2.1,6.3,3.6   c2.2,1.9,3.9,4.3,5.7,6.4c16.5-8.9,16.5-8.9,23.8-1.2c5.9-2.7,8.6-8.4,10.8-13.8c1.8-4.5,4.7-5.7,8.7-6.7c9.3-2.4,18.5-5,27.2-7.3   c0.5-0.9,0.9-1.2,0.9-1.6c3,2.4,7.4,4.3,9.4,7.4c5.2,7.9,12.2,12.5,20.9,15.9c5.4,2.1,9.9,6.7,13.6,9.4c0.6,5.2,1,8.8,1.5,12.3   c2.2,1.4,5.6,3.4,7.7,4.7c-5,6.2-2.4,15-3.2,23.9c-5.2,11.1-4.3,25.1-6,37.6c-4.7,1.9-9.8,3.4-13.3,6.6c-5.4,5.1-9.7,3.9-14.9,0.9   c0-1.7-1.1-3.9-1.1-5.7c-18.8-0.8-30-12.3-39.9-25.8c-2.5-3.4-5.5-4.7-9.7-3.5c-1.3,2.1-2.7,4.2-3.8,6.4c-3.4,6.6-7,7.8-14.1,6.3   c-3.6-0.7-7.5-0.1-11.2,0.5c-5,0.8-6.3,4.6-6.2,9l0,0c-15.6,1-29.6-2.1-37.9-17.4c-2.2-4.1-6.5-8.6-11.6-7.7   c-0.8,0.1-1.6,0.2-2.3,0.2c1.8-11.7,5.5-23,16.3-29.7c3.4-2.1,6.8-4.4,10.1-6.7C263.1,290.9,264.1,288.3,263,284.8",
        "M403.8,125.2c-5.4,3.1-11.9,1-18.3,0.7c-4.3-0.2-8.6-0.3-12.9,0   c-5.8,0.5-11.5,1.6-17.3,2.4c-0.7-1.9-1.4-2.4-1.9-3c-4.8-6.6-8-6.4-11.8,0.6c-0.5,1-1.4,1.8-1.6,2.8c-2.1,10.3-8.1,17.7-16.9,22.8   c-0.3,7.3-3,10.1-10.5,11.5c-3.1,5.6-6,11-9.1,16.6c-8.9-1.3-16.8,0.8-24.3,5.3c-1.7,1-4,1.1-5.8,1.6c-5.7-4.6-11-8.9-15.9-12.9   c-13.7,6.8-26.6,13.5-39.6,19.8c-6.8,3.3-9.6,8.9-10.8,15.3c1.2,4.8,8.7,3.8,7.9,9.5c-0.5,3.7-1.6,7.4-2.1,9.6c5,5,9.8,8.4,12.7,13   c6.4,10.2,14.3,17.8,26,21.4c1.3,0.4,2.1,2.8,2.8,4.4c2.5,5.2,4.7,10.6,7.4,15.7c0.5,0.9,0.9,1.8,1.1,2.7l0,0   c2.5-4.7,5.9-9.7,8.8-15.1c2.1,1.2,4.5,2.1,6.3,3.6c2.2,1.9,3.9,4.3,5.7,6.4c16.5-8.9,16.5-8.9,23.8-1.2c5.9-2.7,8.6-8.4,10.8-13.8   c1.8-4.5,4.7-5.7,8.7-6.7c9.3-2.4,18.5-5,27.2-7.3c0.5-0.9,0.9-1.2,0.9-1.6c0.2-8.8,0.1-17.6,0.7-26.4c0.4-6.3,4-7.8,9.4-4.5   c2,1.3,3.7,3.1,5.7,4.4c4.8,3.4,9.7,5.2,15.8,3c4.1-1.5,8.6-2.1,12.9-2.2c3.2-0.1,6.4,1.4,9.7,2.2c3.8-2.5,3.4-5.3,1-8.6   c-4.6-6.4-4-12.3,2.3-16.7c3.9-2.7,8.6-4.2,12.8-6.3c1.2-0.6,2.9-1.2,3.3-2.3c2.6-6.5,5-13.1,7.4-19.6l-1.1-1.1   c-2.8-1.5-5.5-3.3-8.5-4.4c-3.2-1.3-6.9-1.5-10.1-3c-5.3-2.4-6-5.7-2.9-10.6c0.6-0.9,1.1-1.9,1.7-2.8c3.6-5.3,3.1-10.2-1.1-14.6   C411.2,132.3,406.6,128.4,403.8,125.2z",
        "M408.2,298.7c9.8-1.2,16.3-5,23-11.2c2.5,1.4,5.3,2.5,7.6,4.4   c4,3.2,8.1,5.7,13.5,5.6c4.3,0,7.4,2.2,8.7,8c2.7-2.2,5.1-3.7,6.9-5.9c3.1-3.6,4.7-8.1,9.5-10.6c0.2-0.1,0.4-0.2,0.6-0.3   c3.3-1.5,3.8-6.8,1.6-9.7c-4.2-5.7-6.3-10.4-4.2-18.2c2.5-9.9-0.5-19.9-7.9-28.1c-2.7-3-5.3-6.7-3.9-11.1c1-3,2.6-5.9,4.5-8.4   c2.6-3.3,2.8-6.3,0.5-9.6c-0.8-1.2-1.3-2.5-2-3.8c0.5,0,1,0,1.5,0.1c0.7-2.9,1.9-5.7,2.1-8.7c0.6-7.9,0.7-15.7,1.2-23.6   c0.9-12.1,7-19.2,19.3-19.2c1.2-2.4,1.9-4.4,3.2-6c4.8-6.1,13.1-8.5,16.9-15.8c0.3-0.6,1.4-0.8,2.2-1.1c2.8-1.1,6.6-2.7,9.5-3.8   c-1.5-1.9-3-3.8-4.6-5.6c-3.8-4.3-6.2-11.7-14.3-9c-5-5-9.1-10.2-14.3-14c-5.1-3.7-7.9-8.7-11.2-13.6c-2.2-3.3-5.2-6-7.8-9.1   c-0.9-1-2.2-2.2-2.2-3.4c0-6.2-0.6-12.6,0.6-18.5c2.2-10.7-5.1-19.5-17-17.9c-0.9,5.6-2.2,11.4-2.8,17.3   c-0.9,8.2-3.2,10.9-10.7,8.8c-9.8-2.7-19.5-1.9-29.3-1c-5.6,0.6-7.7,3.8-5,9c1.8,3.6,4.5,6.7,6.7,10.2c2.7,4.2,1.3,7.5-3.9,7.7   c-5.8,0.2-11.7-0.9-15.8-1.2c-4.7-4.9-7.8-8.3-11.7-12.4c-2,1.7-4.7,2.9-5.4,4.9c-1.4,3.6-1,7.4,2,10.6   c5.9,6.3,12.3,12.2,17.3,19.2c4.6,6.5,13.4,11.1,10.6,21.7c2.8,3.2,7.4,7.1,10.4,10.2c4.2,4.4,4.7,9.4,1.1,14.6   c-0.6,0.9-1.1,1.9-1.7,2.8c-3.2,4.9-2.5,8.2,2.9,10.6c3.2,1.4,6.8,1.7,10.1,3c2.9,1.1,5.7,2.9,8.5,4.4l1.1,1.1   c-2.4,6.6-4.8,13.2-7.4,19.6c-0.4,1-2.1,1.7-3.3,2.3c-4.3,2.1-9,3.6-12.8,6.3c-6.3,4.4-6.9,10.3-2.3,16.7c2.4,3.3,2.8,6.1-1,8.6   c-3.3-0.8-6.5-2.3-9.7-2.2c-4.3,0.1-8.8,0.8-12.9,2.2c-6.1,2.2-11,0.4-15.8-3c-2-1.4-3.6-3.2-5.7-4.4c-5.4-3.4-9-1.8-9.4,4.5   c-0.6,8.8-0.5,17.6-0.7,26.4c3,2.4,7.4,4.3,9.4,7.4c5.2,7.9,12.2,12.5,20.9,15.9c5.4,2.1,9.9,6.7,13.6,9.4c0.6,5.2,1,8.8,1.5,12.3   C402.7,295.4,406.1,297.4,408.2,298.7z",
        "M399,360.2c0.8-0.3,1.5-0.6,2.3-1c0.7-0.3,1.5-0.6,2.2-0.5   c7.2,0.6,13.9,2.2,19.2,7.8c3.8,4,8,7.9,12.4,11.2c2.6,1.9,6,4.4,9.4,2.3c0.9-0.6,1.8-1,2.7-1.2c3.1-0.9,6.1-0.1,9.2,0.2   c2.6,0.3,5.2,0.4,7.9,0.6c0.6,0,1.3,0.2,1.7,0c7.1-5.3,12.4-0.3,17,4.8l0,0c0.5-2.8,1-5.5,1.5-8.3c17.8-6.5,35.9-8.8,54.9-5.7   c0.5-0.3,1-0.7,1.5-1h-0.1c5-4,5.6-11.1,1.1-16.3c-1.7-1.9-3.6-4.4-3.8-6.7c-0.6-5.6-1.2-11.5,0.1-16.8c1.3-5,0.1-8.2-3.1-11.3   l-1.1-1.1c-0.2-3-0.4-7.1-0.6-10.1l0,0.1c-6.5-1.7-10.3-6.1-13-12.3c-1.6-3.7-4.6-6.8-7.5-10.9c-3.7,1.5-6.8,2.8-9.8,4.1   c-0.6,0.3-1.3,1.2-1.7,1c-7.6-2.2-14.2,2.8-21.4,2.8c-0.6,0-1.8-2.1-2.4-2.7c-4.8,2.5-6.4,7-9.5,10.6c-1.8,2.1-4.3,3.7-6.9,5.9   c-1.4-5.7-4.5-8-8.7-8c-5.5,0.1-9.5-2.5-13.5-5.6c-2.3-1.8-5.2-3-7.6-4.4c-6.7,6.2-13.3,10.1-23,11.2c-5,6.2-2.4,15-3.2,23.9   C399.8,333.7,400.6,347.7,399,360.2z",
        "M327.6,522c-1.8-7.6-1.6-15.4-0.1-23.3c0.8-4.2,1-8.6,1.1-12.9   c0.1-4.9,1.1-9,4.4-13c7.6-9.1,5.2-19.5,2.9-29.7c-0.2-1-0.8-1.8-1.5-3.5c-3.1,4-5,6.6-6.8,8.9c-4,0-7.9,0-11.9,0   c-2.9-4.3-5.6-8.6-8.5-12.6c-1-1.4-2.4-2.5-3.9-3.4c-5.9-3.4-11.9-6.6-18.9-10.4c-9.4,1.8-15.5-1.5-22.4-13.7   c-6,4.4-11.8,2.1-17.1-0.6c-4.3,1.2-8,2.7-11.9,3.2c-3.6,0.5-6.6-1-9.1-4.3c-4.7-6.4-8.4-7-16.2-4c-8.6,3.3-17.2,6.5-25.9,9.5   c-8.5,3-14,8.8-17.4,16.9c-0.3,0.8-0.9,1.5-1.1,2.3c-2.1,7.4-10.3,6.6-14.9,11c-4.3,4.1-10.5,3.2-16.1,0.8c-4-1.7-12-0.5-19,2.6   c0.7,1.7,1,3.8,2.2,5.1c1.7,2,4,3.4,6.1,5.1c7.8,6.4,8.1,7.8,5.1,17.4c-0.9,3.1-1.3,6.3-1.7,9.5c-0.6,5,1.8,8.1,6.1,10.8   c5.9,3.7,11.2,8.3,17,12.2c5.4,3.6,11.2,5.1,18,3.1c4-1.2,8.6-0.2,13-0.2c2.7,6.9-0.9,18.7,12.7,15.5c5.9,7.8,10.3,17.2,22.7,17.2   c0.7-2.5,1.3-5.2,2.2-8.5c5.4-2.9,10.9-7.1,18.5-7.6c5.7-0.4,7.6-5.5,6.5-12.1c3.6,2.8,6.1,4.8,9.6,7.5c3.2-0.9,7.1-2.2,11-2.8   c2.1-0.3,4.5,0.6,6.7,1c4.1,0.7,8.2,1.7,12.4,2.2c6.9,0.9,13.9,1.6,20.8,2.3c3.9,0.4,7.4-0.6,10.8-3.2   C318,516.3,321.9,517,327.6,522z",
        "M482.8,384.2c-0.4,2.3-0.8,4.6-1.1,7c-0.6,4.3,0.3,8.2,3.1,12   c1.5,2,1.1,5.5,1.3,8.3c0.2,2.4,0,4.9,0,7.7c8.1,3.1,15.9,6.8,22.1,13.6c2.5,2.8,6.7,4,10.4,6c1.6-1.8,2.8-3.1,3.8-4.2   c1.4,4.2,2.3,8.5,4.3,12.3c2.5,5,1.8,9.2-1.2,13.6c-2,3-4.4,6.2-4.9,9.6c-0.9,5.6-5.7,7.2-8.8,10.8c2.7,3.1,5.4,6.2,8.7,9.8   c-2.4,1.6-4.6,4.3-6.9,4.4c-7.4,0.4-10.4,5.6-13.5,10.9c-0.6,1.1-1.1,2.2-1.7,3.4c-3.9,8-12.9,11-21.5,6.3c-2.8-1.6-7.6-1.2-11-1.8   l0,0c-1.6-10.1-5.9-18.1-15.7-22.4c-0.8-5.8,2.1-12-6.8-13.2c-4.7,6.4-11.3,7.1-18.8,4.3c-2-0.7-4.5-0.2-6.7-0.1   c-2.6,0.1-5.2,0.5-7.9,0.7c-0.4-0.5-0.8-0.9-1.2-1.4c1.8-3.3,3.6-6.6,5.4-9.9c-2.6-1.7-5-3.2-7.9-5c3.5-2.7,6.4-4.8,9.1-6.8   c-0.6-1.6-1-2.5-1.4-3.5c-0.8-2.3-1.3-4.1-1.3-5.6c-0.1-3.7,2.5-5.1,8.6-5.1c2.3,0,4.5,0,6.1,0c3.4-4.6,5.7-9.1,9.2-12.2   c4.6-4,5.2-9.1,6.6-14.2c0.5-1.7,0.2-3.8,1.2-5c3.4-4.1,3.4-8.7,3.3-13.6c-0.1-5.8,0-11.6,0-17.4l-0.6-0.6   c-0.4-0.2-1.1-0.4-1.1-0.6c0-0.6,0.7-3,0.9-3.6l0,0c3.1-0.9,6.1-0.1,9.2,0.2c2.6,0.3,5.2,0.4,7.9,0.6c0.6,0,1.3,0.2,1.7,0   C472.9,374.2,478.3,379.2,482.8,384.2",
        "M447.1,378.7c-0.2,0.6-0.8,3-0.9,3.6c0,0.2,0.7,0.4,1.1,0.6   l0.6,0.6c0,5.8-0.1,11.6,0,17.4c0.1,4.8,0.1,9.5-3.3,13.6c-1,1.2-0.8,3.3-1.2,5c-1.4,5.1-2,10.3-6.6,14.2   c-3.6,3.1-5.9,7.6-9.2,12.2c-1.6,0-3.9,0-6.1,0c-6.1,0-8.7,1.3-8.6,5.1l0,0c-10,1.8-18.3-3.4-24.8-9.5c-4.7,1.4-9,2.7-13.4,4.1   c-0.1,0.5-0.3,0.9-0.4,1.4c1.5,0.8,1.7,0.7,1.7,0.7c4.2-1.2,4,2,4.6,4.4c-7.3,6-9.3,6.1-15.9,0.7c-3-2.5-6.5-2.1-9,1.1   c-0.9,1.1-1.6,2.4-2.8,4.2c-4.4-3.3-9.7-5.3-14.9-3.7c-0.4-3.7-1.2-7.4-2.1-11.1c-0.2-1-0.8-1.8-1.5-3.5c-3.1,4-5,6.6-6.8,8.9   c-4,0-7.9,0-11.9,0c-2.9-4.3-5.6-8.6-8.5-12.6c-0.5-0.7-1.1-1.3-1.7-1.8l0,0c0.4-4.5,1-9.5,1.4-13.9c3.4-0.3,5.8-0.5,8.2-0.6   c2.4-0.1,4.9,0,7.3,0c7.7,6.7,11.7,7,20.2,1.1c0.9-7-0.7-9-7.3-8.4c-1.7,0.1-3.4,0.9-5.1,1.1c-2.8,0.3-5.3-0.4-5-3.9   c0.7-8.2-3.2-13.4-10.1-16.6c-0.4-1.9-1.5-3.9-1.1-5.2c1.4-3.9-0.6-6.2-3-8.5c-3.8-3.7-8.4-5.7-13.5-6.1c-6.1-0.4-9.7-3.9-11.7-9.1   c-1-2.6-1-5.6-1.2-8.4c0-0.2,0-0.3,0-0.5c-0.1-4.4,1.2-8.1,6.2-9c3.7-0.6,7.6-1.2,11.2-0.5c7.1,1.5,10.7,0.2,14.1-6.3   c1.1-2.2,2.5-4.2,3.8-6.4c4.2-1.2,7.2,0.1,9.7,3.5c9.9,13.4,21.1,25,39.9,25.8c0,1.8,1.1,4,1.1,5.7c5.1,3.1,9.5,4.2,14.9-0.9   c3.4-3.2,8.5-4.7,13.3-6.6c0.8-0.3,1.5-0.6,2.3-1c0.7-0.3,1.5-0.6,2.2-0.5c7.2,0.6,13.9,2.2,19.2,7.8c3.8,4,8,7.9,12.4,11.2   c2.6,1.9,6,4.4,9.4,2.3C445.3,379.3,446.2,378.9,447.1,378.7",
        "M412.9,450.9c0,1.5,0.5,3.3,1.3,5.6c0.3,0.9,0.7,1.9,1.4,3.5   c-2.7,2-5.5,4.2-9.1,6.8c3,1.9,5.3,3.4,7.9,5c-1.8,3.3-3.6,6.6-5.4,9.9c0.4,0.5,0.8,0.9,1.2,1.4c2.6-0.2,5.2-0.6,7.9-0.7   c2.2-0.1,4.7-0.7,6.7,0.1c7.5,2.8,14.1,2.2,18.8-4.3c8.8,1.3,5.9,7.5,6.8,13.2c9.7,4.3,14.1,12.3,15.7,22.4   c0.4,2.3,0.6,4.8,0.7,7.3c-8.3-2.9-16.3-5.2-23.9-8.6c-11.4-5.2-25.6-1.6-32.1,9.1c-7,11.5-18.2,17.2-30.2,23.1   c-2,3.8,1.5,6.8,3.2,10c2.2,4.1,5,7.9,7.6,11.9c-1.6,1.4-2.4,2.1-3.4,3.1c-5.5-4.8-12.3-3.9-18.8-3.5c-4,0.2-7-1.2-10.2-3.4   c-6.1-4.3-12.1-8.7-18.6-12.3c-6.5-3.6-6.2-10.2-8.4-15.9c-0.4-1.1-0.6-2.3-1.1-3.4c-1.5-3.1-2.5-6.2-3.3-9.3   c-1.8-7.6-1.6-15.4-0.1-23.3c0.8-4.2,1-8.6,1.1-12.9c0.1-4.9,1.1-9,4.4-13c4.8-5.8,5.6-12.1,4.9-18.6c5.2-1.6,10.5,0.4,14.9,3.7   c1.2-1.8,1.9-3.1,2.8-4.2c2.5-3.2,6-3.6,9-1.1c6.6,5.4,8.5,5.3,15.9-0.7c-0.6-2.4-0.4-5.6-4.6-4.4c0,0-0.2,0.1-1.7-0.7   c0.1-0.5,0.3-0.9,0.4-1.4c4.4-1.3,8.7-2.6,13.4-4.1C394.6,447.5,402.8,452.7,412.9,450.9L412.9,450.9z",
    )

    fun printSvg(context: Context) {
        try {
            // 加载SVG文件
            val inputStream: InputStream = context.assets.open("hc.svg")
            // 创建XML解析器
            val factory = XmlPullParserFactory.newInstance()
            val parser: XmlPullParser = factory.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)

            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (parser.name == "path") {
                            // 如果是<path>标签
                            val name = parser.getAttributeValue(null, "name")
                            // 打印d属性的值
                            val pathData = parser.getAttributeValue(null, "d")
                            Log.e("Svg", name)
                            Log.e("Svg", pathData)
                        }
                    }
                }
                eventType = parser.next()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}