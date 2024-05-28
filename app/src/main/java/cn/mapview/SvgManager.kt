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

//    val hcNames = arrayOf(
//        "平谷区",
//        "密云县",
//        "门头沟区",
//        "昌平区",
//        "延庆县",
//        "怀柔区",
//        "顺义区",
//        "房山区",
//        "通州区",
//        "北京市区",
//        "大兴区",
//    )
//    val hcSvgPaths = arrayOf(
//        "M611,256.8c0.4,3.1,1.7,6.1,3.4,9c5.9,10.1,12.3,19.9,18.5,29.9 c4.2,6.7,11.2,9.1,18.3,11.9c0,4.9,0,10,0,15.7c-5.2-0.9-7.5,2-9.3,6.1c-3.2,7.1-10.2,10.5-15.9,14.9c-3.3,2.6-8.7,2.7-13.3,3.6 c-3.7,0.7-6.6,2.3-9,5.1c-4.7,5.7-11,8.2-18,7.2c-12.7-2-21.9,4.1-31,11.4c-0.6,0.5-1.4,0.9-2.3,1.5c-3.2-1.1-8.7-2.9-11.8-3.9l0,0 c5-4,5.6-11.1,1.1-16.3c-1.7-1.9-3.6-4.4-3.8-6.7c-0.6-5.6-1.2-11.5,0.1-16.8c1.3-5,0.1-8.2-3.1-11.3l-1.1-1.1 c-0.2-3-0.4-7.1-0.6-10.1l0,0.1c0.7,0.2,1.4,0.3,2.1,0.4c2.5-0.4,5.3-0.9,7.9-1.4c-0.7-3.2-1.4-6.2-2.2-9.7 c4.4-4.7,7.3-10.5,6.9-17.5c-0.3-5.1,1.9-7.9,6.7-9.7c7.1-2.6,13.8-6.1,20.9-8.8c7.9-3.1,15.5-7.8,24.8-4.6 C603.3,256.7,607.9,256.4,611,256.8",
//        "M611,256.8c-3.2-0.4-7.8-0.1-10.7-1.1c-9.2-3.1-16.8,1.6-24.8,4.6 c-7,2.7-13.8,6.3-20.9,8.8c-4.9,1.8-7.1,4.6-6.7,9.7c0.5,7-2.4,12.8-6.9,17.5c0.8,3.5,1.5,6.5,2.2,9.7c-2.6,0.5-5.4,1-7.9,1.4 c-7.9-1.2-12.2-5.9-15.1-12.8c-1.6-3.7-4.6-6.8-7.5-10.9c-3.7,1.5-6.8,2.8-9.8,4.1c-0.6,0.3-1.3,1.2-1.7,1 c-7.6-2.2-14.2,2.8-21.4,2.8c-0.6,0-1.8-2.1-2.4-2.7c0.2-0.1,0.4-0.2,0.6-0.3c3.3-1.5,3.8-6.8,1.6-9.7c-4.2-5.7-6.3-10.4-4.2-18.2 c2.5-9.9-0.5-19.9-7.9-28.1c-2.7-3-5.3-6.7-3.9-11.1c1-3,2.6-5.9,4.5-8.4c2.6-3.3,2.8-6.3,0.5-9.6c-0.8-1.2-1.3-2.5-2-3.8 c0.5,0,1,0,1.5,0.1c0.7-2.9,1.9-5.7,2.1-8.7c0.6-7.9,0.7-15.7,1.2-23.6c0.9-12.1,7-19.2,19.3-19.2c1.2-2.4,1.9-4.4,3.2-6 c4.8-6.1,13.1-8.5,16.9-15.8c0.3-0.6,1.4-0.8,2.2-1.1c2.8-1.1,6.6-2.7,9.5-3.8c4.8,6.2,9.3,12.8,14.2,19 c5.3,6.6,11.6,12.4,20.7,12.3c6.6-0.1,13.1-2,19.7-1.9c7.1,0.1,14.4,1,21.3,2.8c8.6,2.3,16.8,6.1,25.4,8.8c7.9,2.5,15.4,1.5,23.1-2 c8.8-4.1,18.5-5.1,28-3.3c4.2,0.8,8.1,3.5,11.7,5.2c1.1,7.9-0.3,9.3-7.2,7.5c-4.2-1.1-8.9-0.8-10.9,2.5c-1.6,2.8-0.7,7.3-0.2,11 c0.4,2.6,1.9,5,3.1,7.9c-5.8,4.9-13.9,2.5-21,4.7c-10.4-8-26.2-4.7-31.9,7.5c1,2.3,2.1,4.9,3.4,7.8c-3.2,5.9-13.5-0.7-15.6,10.1 c1.5,3.1,3.8,7.9,6.2,12.6c2.2,4.3,2.9,8.7,0.5,12.9C611.1,250.2,610.6,253.5,611,256.8",
//        "M284.8,354.8c0,0.2,0,0.3,0,0.5c0.2,2.8,0.2,5.8,1.2,8.4 c2,5.2,5.6,8.7,11.7,9.1c5.1,0.4,9.7,2.4,13.5,6.1c2.4,2.3,4.3,4.6,3,8.5c-0.5,1.4,0.6,3.3,1.1,5.2c6.9,3.3,10.8,8.5,10.1,16.6 c-0.3,3.5,2.2,4.2,5,3.9c1.7-0.2,3.4-0.9,5.1-1.1c6.6-0.6,8.3,1.4,7.3,8.4c-8.6,5.8-12.6,5.6-20.2-1.1c-2.4,0-4.9-0.1-7.3,0 c-2.4,0.1-4.9,0.3-8.2,0.6c-0.4,4.4-1,9.4-1.4,13.9l0,0c-0.7-0.6-1.4-1.1-2.2-1.5c-5.9-3.4-11.9-6.6-18.9-10.4 c-9.4,1.8-15.5-1.5-22.4-13.7c-6,4.4-11.8,2.1-17.1-0.6c-4.3,1.2-8,2.7-11.9,3.2c-3.6,0.5-6.6-1-9.1-4.3c-4.7-6.4-8.4-7-16.2-4 c-8.6,3.3-17.2,6.5-25.9,9.5c-8.5,3-14,8.8-17.4,16.9c-0.3,0.8-0.9,1.5-1.1,2.3c-1.8,6.1-7.7,6.7-12.3,9.2l0,0 c-1.1-1.3-2.6-5.1-4-5.7c-7.8-3.4-8.7-10.4-9.6-17.5c-0.5-4-0.9-8.1-2.3-11.8c-1.9-4.9-6.2-7.6-11.1-9 c-13.2-3.8-12.9-11.5-7.9-21.4c5.1-10,13.8-16.5,23.1-21.8c14.4-8.2,28.8-16.7,45.5-19.8c2.8-0.5,5.6-0.8,8.4-1.1 c4.2-0.6,8.3-1.1,11.4-1.5c2.8-6.3,5-11.1,7.7-17c3.2,3.6,5.2,6.1,7.4,8.4c3.8,4,7.3,7.9,13.4,7.8c0.7,0,1.5-0.1,2.3-0.2 c5.1-0.9,9.4,3.6,11.6,7.7C255.2,352.6,269.3,355.8,284.8,354.8",
//        "M263,284.8c2.5-4.7,5.9-9.7,8.8-15.1c2.1,1.2,4.5,2.1,6.3,3.6 c2.2,1.9,3.9,4.3,5.7,6.4c16.5-8.9,16.5-8.9,23.8-1.2c5.9-2.7,8.6-8.4,10.8-13.8c1.8-4.5,4.7-5.7,8.7-6.7c9.3-2.4,18.5-5,27.2-7.3 c0.5-0.9,0.9-1.2,0.9-1.6c3,2.4,7.4,4.3,9.4,7.4c5.2,7.9,12.2,12.5,20.9,15.9c5.4,2.1,9.9,6.7,13.6,9.4c0.6,5.2,1,8.8,1.5,12.3 c2.2,1.4,5.6,3.4,7.7,4.7c-5,6.2-2.4,15-3.2,23.9c-5.2,11.1-4.3,25.1-6,37.6c-4.7,1.9-9.8,3.4-13.3,6.6c-5.4,5.1-9.7,3.9-14.9,0.9 c0-1.7-1.1-3.9-1.1-5.7c-18.8-0.8-30-12.3-39.9-25.8c-2.5-3.4-5.5-4.7-9.7-3.5c-1.3,2.1-2.7,4.2-3.8,6.4c-3.4,6.6-7,7.8-14.1,6.3 c-3.6-0.7-7.5-0.1-11.2,0.5c-5,0.8-6.3,4.6-6.2,9l0,0c-15.6,1-29.6-2.1-37.9-17.4c-2.2-4.1-6.5-8.6-11.6-7.7 c-0.8,0.1-1.6,0.2-2.3,0.2c1.8-11.7,5.5-23,16.3-29.7c3.4-2.1,6.8-4.4,10.1-6.7C263.1,290.9,264.1,288.3,263,284.8",
//        "M403.8,125.2c-5.4,3.1-11.9,1-18.3,0.7c-4.3-0.2-8.6-0.3-12.9,0 c-5.8,0.5-11.5,1.6-17.3,2.4c-0.7-1.9-1.4-2.4-1.9-3c-4.8-6.6-8-6.4-11.8,0.6c-0.5,1-1.4,1.8-1.6,2.8c-2.1,10.3-8.1,17.7-16.9,22.8 c-0.3,7.3-3,10.1-10.5,11.5c-3.1,5.6-6,11-9.1,16.6c-8.9-1.3-16.8,0.8-24.3,5.3c-1.7,1-4,1.1-5.8,1.6c-5.7-4.6-11-8.9-15.9-12.9 c-13.7,6.8-26.6,13.5-39.6,19.8c-6.8,3.3-9.6,8.9-10.8,15.3c1.2,4.8,8.7,3.8,7.9,9.5c-0.5,3.7-1.6,7.4-2.1,9.6c5,5,9.8,8.4,12.7,13 c6.4,10.2,14.3,17.8,26,21.4c1.3,0.4,2.1,2.8,2.8,4.4c2.5,5.2,4.7,10.6,7.4,15.7c0.5,0.9,0.9,1.8,1.1,2.7l0,0 c2.5-4.7,5.9-9.7,8.8-15.1c2.1,1.2,4.5,2.1,6.3,3.6c2.2,1.9,3.9,4.3,5.7,6.4c16.5-8.9,16.5-8.9,23.8-1.2c5.9-2.7,8.6-8.4,10.8-13.8 c1.8-4.5,4.7-5.7,8.7-6.7c9.3-2.4,18.5-5,27.2-7.3c0.5-0.9,0.9-1.2,0.9-1.6c0.2-8.8,0.1-17.6,0.7-26.4c0.4-6.3,4-7.8,9.4-4.5 c2,1.3,3.7,3.1,5.7,4.4c4.8,3.4,9.7,5.2,15.8,3c4.1-1.5,8.6-2.1,12.9-2.2c3.2-0.1,6.4,1.4,9.7,2.2c3.8-2.5,3.4-5.3,1-8.6 c-4.6-6.4-4-12.3,2.3-16.7c3.9-2.7,8.6-4.2,12.8-6.3c1.2-0.6,2.9-1.2,3.3-2.3c2.6-6.5,5-13.1,7.4-19.6l-1.1-1.1 c-2.8-1.5-5.5-3.3-8.5-4.4c-3.2-1.3-6.9-1.5-10.1-3c-5.3-2.4-6-5.7-2.9-10.6c0.6-0.9,1.1-1.9,1.7-2.8c3.6-5.3,3.1-10.2-1.1-14.6 C411.2,132.3,406.6,128.4,403.8,125.2z",
//        "M408.2,298.7c9.8-1.2,16.3-5,23-11.2c2.5,1.4,5.3,2.5,7.6,4.4 c4,3.2,8.1,5.7,13.5,5.6c4.3,0,7.4,2.2,8.7,8c2.7-2.2,5.1-3.7,6.9-5.9c3.1-3.6,4.7-8.1,9.5-10.6c0.2-0.1,0.4-0.2,0.6-0.3 c3.3-1.5,3.8-6.8,1.6-9.7c-4.2-5.7-6.3-10.4-4.2-18.2c2.5-9.9-0.5-19.9-7.9-28.1c-2.7-3-5.3-6.7-3.9-11.1c1-3,2.6-5.9,4.5-8.4 c2.6-3.3,2.8-6.3,0.5-9.6c-0.8-1.2-1.3-2.5-2-3.8c0.5,0,1,0,1.5,0.1c0.7-2.9,1.9-5.7,2.1-8.7c0.6-7.9,0.7-15.7,1.2-23.6 c0.9-12.1,7-19.2,19.3-19.2c1.2-2.4,1.9-4.4,3.2-6c4.8-6.1,13.1-8.5,16.9-15.8c0.3-0.6,1.4-0.8,2.2-1.1c2.8-1.1,6.6-2.7,9.5-3.8 c-1.5-1.9-3-3.8-4.6-5.6c-3.8-4.3-6.2-11.7-14.3-9c-5-5-9.1-10.2-14.3-14c-5.1-3.7-7.9-8.7-11.2-13.6c-2.2-3.3-5.2-6-7.8-9.1 c-0.9-1-2.2-2.2-2.2-3.4c0-6.2-0.6-12.6,0.6-18.5c2.2-10.7-5.1-19.5-17-17.9c-0.9,5.6-2.2,11.4-2.8,17.3 c-0.9,8.2-3.2,10.9-10.7,8.8c-9.8-2.7-19.5-1.9-29.3-1c-5.6,0.6-7.7,3.8-5,9c1.8,3.6,4.5,6.7,6.7,10.2c2.7,4.2,1.3,7.5-3.9,7.7 c-5.8,0.2-11.7-0.9-15.8-1.2c-4.7-4.9-7.8-8.3-11.7-12.4c-2,1.7-4.7,2.9-5.4,4.9c-1.4,3.6-1,7.4,2,10.6 c5.9,6.3,12.3,12.2,17.3,19.2c4.6,6.5,13.4,11.1,10.6,21.7c2.8,3.2,7.4,7.1,10.4,10.2c4.2,4.4,4.7,9.4,1.1,14.6 c-0.6,0.9-1.1,1.9-1.7,2.8c-3.2,4.9-2.5,8.2,2.9,10.6c3.2,1.4,6.8,1.7,10.1,3c2.9,1.1,5.7,2.9,8.5,4.4l1.1,1.1 c-2.4,6.6-4.8,13.2-7.4,19.6c-0.4,1-2.1,1.7-3.3,2.3c-4.3,2.1-9,3.6-12.8,6.3c-6.3,4.4-6.9,10.3-2.3,16.7c2.4,3.3,2.8,6.1-1,8.6 c-3.3-0.8-6.5-2.3-9.7-2.2c-4.3,0.1-8.8,0.8-12.9,2.2c-6.1,2.2-11,0.4-15.8-3c-2-1.4-3.6-3.2-5.7-4.4c-5.4-3.4-9-1.8-9.4,4.5 c-0.6,8.8-0.5,17.6-0.7,26.4c3,2.4,7.4,4.3,9.4,7.4c5.2,7.9,12.2,12.5,20.9,15.9c5.4,2.1,9.9,6.7,13.6,9.4c0.6,5.2,1,8.8,1.5,12.3 C402.7,295.4,406.1,297.4,408.2,298.7z",
//        "M399,360.2c0.8-0.3,1.5-0.6,2.3-1c0.7-0.3,1.5-0.6,2.2-0.5 c7.2,0.6,13.9,2.2,19.2,7.8c3.8,4,8,7.9,12.4,11.2c2.6,1.9,6,4.4,9.4,2.3c0.9-0.6,1.8-1,2.7-1.2c3.1-0.9,6.1-0.1,9.2,0.2 c2.6,0.3,5.2,0.4,7.9,0.6c0.6,0,1.3,0.2,1.7,0c7.1-5.3,12.4-0.3,17,4.8l0,0c0.5-2.8,1-5.5,1.5-8.3c17.8-6.5,35.9-8.8,54.9-5.7 c0.5-0.3,1-0.7,1.5-1h-0.1c5-4,5.6-11.1,1.1-16.3c-1.7-1.9-3.6-4.4-3.8-6.7c-0.6-5.6-1.2-11.5,0.1-16.8c1.3-5,0.1-8.2-3.1-11.3 l-1.1-1.1c-0.2-3-0.4-7.1-0.6-10.1l0,0.1c-6.5-1.7-10.3-6.1-13-12.3c-1.6-3.7-4.6-6.8-7.5-10.9c-3.7,1.5-6.8,2.8-9.8,4.1 c-0.6,0.3-1.3,1.2-1.7,1c-7.6-2.2-14.2,2.8-21.4,2.8c-0.6,0-1.8-2.1-2.4-2.7c-4.8,2.5-6.4,7-9.5,10.6c-1.8,2.1-4.3,3.7-6.9,5.9 c-1.4-5.7-4.5-8-8.7-8c-5.5,0.1-9.5-2.5-13.5-5.6c-2.3-1.8-5.2-3-7.6-4.4c-6.7,6.2-13.3,10.1-23,11.2c-5,6.2-2.4,15-3.2,23.9 C399.8,333.7,400.6,347.7,399,360.2z",
//        "M327.6,522c-1.8-7.6-1.6-15.4-0.1-23.3c0.8-4.2,1-8.6,1.1-12.9 c0.1-4.9,1.1-9,4.4-13c7.6-9.1,5.2-19.5,2.9-29.7c-0.2-1-0.8-1.8-1.5-3.5c-3.1,4-5,6.6-6.8,8.9c-4,0-7.9,0-11.9,0 c-2.9-4.3-5.6-8.6-8.5-12.6c-1-1.4-2.4-2.5-3.9-3.4c-5.9-3.4-11.9-6.6-18.9-10.4c-9.4,1.8-15.5-1.5-22.4-13.7 c-6,4.4-11.8,2.1-17.1-0.6c-4.3,1.2-8,2.7-11.9,3.2c-3.6,0.5-6.6-1-9.1-4.3c-4.7-6.4-8.4-7-16.2-4c-8.6,3.3-17.2,6.5-25.9,9.5 c-8.5,3-14,8.8-17.4,16.9c-0.3,0.8-0.9,1.5-1.1,2.3c-2.1,7.4-10.3,6.6-14.9,11c-4.3,4.1-10.5,3.2-16.1,0.8c-4-1.7-12-0.5-19,2.6 c0.7,1.7,1,3.8,2.2,5.1c1.7,2,4,3.4,6.1,5.1c7.8,6.4,8.1,7.8,5.1,17.4c-0.9,3.1-1.3,6.3-1.7,9.5c-0.6,5,1.8,8.1,6.1,10.8 c5.9,3.7,11.2,8.3,17,12.2c5.4,3.6,11.2,5.1,18,3.1c4-1.2,8.6-0.2,13-0.2c2.7,6.9-0.9,18.7,12.7,15.5c5.9,7.8,10.3,17.2,22.7,17.2 c0.7-2.5,1.3-5.2,2.2-8.5c5.4-2.9,10.9-7.1,18.5-7.6c5.7-0.4,7.6-5.5,6.5-12.1c3.6,2.8,6.1,4.8,9.6,7.5c3.2-0.9,7.1-2.2,11-2.8 c2.1-0.3,4.5,0.6,6.7,1c4.1,0.7,8.2,1.7,12.4,2.2c6.9,0.9,13.9,1.6,20.8,2.3c3.9,0.4,7.4-0.6,10.8-3.2 C318,516.3,321.9,517,327.6,522z",
//        "M482.8,384.2c-0.4,2.3-0.8,4.6-1.1,7c-0.6,4.3,0.3,8.2,3.1,12 c1.5,2,1.1,5.5,1.3,8.3c0.2,2.4,0,4.9,0,7.7c8.1,3.1,15.9,6.8,22.1,13.6c2.5,2.8,6.7,4,10.4,6c1.6-1.8,2.8-3.1,3.8-4.2 c1.4,4.2,2.3,8.5,4.3,12.3c2.5,5,1.8,9.2-1.2,13.6c-2,3-4.4,6.2-4.9,9.6c-0.9,5.6-5.7,7.2-8.8,10.8c2.7,3.1,5.4,6.2,8.7,9.8 c-2.4,1.6-4.6,4.3-6.9,4.4c-7.4,0.4-10.4,5.6-13.5,10.9c-0.6,1.1-1.1,2.2-1.7,3.4c-3.9,8-12.9,11-21.5,6.3c-2.8-1.6-7.6-1.2-11-1.8 l0,0c-1.6-10.1-5.9-18.1-15.7-22.4c-0.8-5.8,2.1-12-6.8-13.2c-4.7,6.4-11.3,7.1-18.8,4.3c-2-0.7-4.5-0.2-6.7-0.1 c-2.6,0.1-5.2,0.5-7.9,0.7c-0.4-0.5-0.8-0.9-1.2-1.4c1.8-3.3,3.6-6.6,5.4-9.9c-2.6-1.7-5-3.2-7.9-5c3.5-2.7,6.4-4.8,9.1-6.8 c-0.6-1.6-1-2.5-1.4-3.5c-0.8-2.3-1.3-4.1-1.3-5.6c-0.1-3.7,2.5-5.1,8.6-5.1c2.3,0,4.5,0,6.1,0c3.4-4.6,5.7-9.1,9.2-12.2 c4.6-4,5.2-9.1,6.6-14.2c0.5-1.7,0.2-3.8,1.2-5c3.4-4.1,3.4-8.7,3.3-13.6c-0.1-5.8,0-11.6,0-17.4l-0.6-0.6 c-0.4-0.2-1.1-0.4-1.1-0.6c0-0.6,0.7-3,0.9-3.6l0,0c3.1-0.9,6.1-0.1,9.2,0.2c2.6,0.3,5.2,0.4,7.9,0.6c0.6,0,1.3,0.2,1.7,0 C472.9,374.2,478.3,379.2,482.8,384.2",
//        "M447.1,378.7c-0.2,0.6-0.8,3-0.9,3.6c0,0.2,0.7,0.4,1.1,0.6 l0.6,0.6c0,5.8-0.1,11.6,0,17.4c0.1,4.8,0.1,9.5-3.3,13.6c-1,1.2-0.8,3.3-1.2,5c-1.4,5.1-2,10.3-6.6,14.2 c-3.6,3.1-5.9,7.6-9.2,12.2c-1.6,0-3.9,0-6.1,0c-6.1,0-8.7,1.3-8.6,5.1l0,0c-10,1.8-18.3-3.4-24.8-9.5c-4.7,1.4-9,2.7-13.4,4.1 c-0.1,0.5-0.3,0.9-0.4,1.4c1.5,0.8,1.7,0.7,1.7,0.7c4.2-1.2,4,2,4.6,4.4c-7.3,6-9.3,6.1-15.9,0.7c-3-2.5-6.5-2.1-9,1.1 c-0.9,1.1-1.6,2.4-2.8,4.2c-4.4-3.3-9.7-5.3-14.9-3.7c-0.4-3.7-1.2-7.4-2.1-11.1c-0.2-1-0.8-1.8-1.5-3.5c-3.1,4-5,6.6-6.8,8.9 c-4,0-7.9,0-11.9,0c-2.9-4.3-5.6-8.6-8.5-12.6c-0.5-0.7-1.1-1.3-1.7-1.8l0,0c0.4-4.5,1-9.5,1.4-13.9c3.4-0.3,5.8-0.5,8.2-0.6 c2.4-0.1,4.9,0,7.3,0c7.7,6.7,11.7,7,20.2,1.1c0.9-7-0.7-9-7.3-8.4c-1.7,0.1-3.4,0.9-5.1,1.1c-2.8,0.3-5.3-0.4-5-3.9 c0.7-8.2-3.2-13.4-10.1-16.6c-0.4-1.9-1.5-3.9-1.1-5.2c1.4-3.9-0.6-6.2-3-8.5c-3.8-3.7-8.4-5.7-13.5-6.1c-6.1-0.4-9.7-3.9-11.7-9.1 c-1-2.6-1-5.6-1.2-8.4c0-0.2,0-0.3,0-0.5c-0.1-4.4,1.2-8.1,6.2-9c3.7-0.6,7.6-1.2,11.2-0.5c7.1,1.5,10.7,0.2,14.1-6.3 c1.1-2.2,2.5-4.2,3.8-6.4c4.2-1.2,7.2,0.1,9.7,3.5c9.9,13.4,21.1,25,39.9,25.8c0,1.8,1.1,4,1.1,5.7c5.1,3.1,9.5,4.2,14.9-0.9 c3.4-3.2,8.5-4.7,13.3-6.6c0.8-0.3,1.5-0.6,2.3-1c0.7-0.3,1.5-0.6,2.2-0.5c7.2,0.6,13.9,2.2,19.2,7.8c3.8,4,8,7.9,12.4,11.2 c2.6,1.9,6,4.4,9.4,2.3C445.3,379.3,446.2,378.9,447.1,378.7",
//        "M412.9,450.9c0,1.5,0.5,3.3,1.3,5.6c0.3,0.9,0.7,1.9,1.4,3.5 c-2.7,2-5.5,4.2-9.1,6.8c3,1.9,5.3,3.4,7.9,5c-1.8,3.3-3.6,6.6-5.4,9.9c0.4,0.5,0.8,0.9,1.2,1.4c2.6-0.2,5.2-0.6,7.9-0.7 c2.2-0.1,4.7-0.7,6.7,0.1c7.5,2.8,14.1,2.2,18.8-4.3c8.8,1.3,5.9,7.5,6.8,13.2c9.7,4.3,14.1,12.3,15.7,22.4 c0.4,2.3,0.6,4.8,0.7,7.3c-8.3-2.9-16.3-5.2-23.9-8.6c-11.4-5.2-25.6-1.6-32.1,9.1c-7,11.5-18.2,17.2-30.2,23.1 c-2,3.8,1.5,6.8,3.2,10c2.2,4.1,5,7.9,7.6,11.9c-1.6,1.4-2.4,2.1-3.4,3.1c-5.5-4.8-12.3-3.9-18.8-3.5c-4,0.2-7-1.2-10.2-3.4 c-6.1-4.3-12.1-8.7-18.6-12.3c-6.5-3.6-6.2-10.2-8.4-15.9c-0.4-1.1-0.6-2.3-1.1-3.4c-1.5-3.1-2.5-6.2-3.3-9.3 c-1.8-7.6-1.6-15.4-0.1-23.3c0.8-4.2,1-8.6,1.1-12.9c0.1-4.9,1.1-9,4.4-13c4.8-5.8,5.6-12.1,4.9-18.6c5.2-1.6,10.5,0.4,14.9,3.7 c1.2-1.8,1.9-3.1,2.8-4.2c2.5-3.2,6-3.6,9-1.1c6.6,5.4,8.5,5.3,15.9-0.7c-0.6-2.4-0.4-5.6-4.6-4.4c0,0-0.2,0.1-1.7-0.7 c0.1-0.5,0.3-0.9,0.4-1.4c4.4-1.3,8.7-2.6,13.4-4.1C394.6,447.5,402.8,452.7,412.9,450.9L412.9,450.9z",
//    )

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
        /**0  北京   */
        "M421.139,189.75L420.782,186.894L419.95,184.989L425.045,182.863L425.426,181.18L424.23699999999997,176.413H422.56899999999996L415.90299999999996,172.964L412.21299999999997,176.654C412.21299999999997,176.654,411.08799999999997,183.239,411.381,181.534C411.66999999999996,179.82999999999998,407.688,185.822,407.688,185.822L407.094,190.108L407.926,192.371L412.807,191.537L416.5,192.608L418.284,190.941L421.139,189.75Z"
        /**1  天津   */
        ,
        /**1  天津   */
        "M430.413,200.491C430.413,200.491,428.581,202.163,427.094,201.775C425.604,201.387,421.615,200.347,421.615,200.347L421.378,198.918L421.14,189.749L424.83,188.082L424.234,186.416L425.044,182.859L425.429,181.17600000000002L427.33,182.126L428.28299999999996,185.22400000000002L427.92699999999996,187.246L431.974,190.104L432.568,192.36700000000002L430.18899999999996,194.03500000000003L429.35499999999996,197.84400000000002L430.413,200.491Z"
        /**2  上海   */
        ,
        /**2  上海   */
        "M484.32,292.485L480.322,289.153C480.322,289.153,481.189,288.778,482.22,286.90200000000004C483.25100000000003,285.02700000000004,483.646,282.61600000000004,483.646,282.61600000000004L487.933,284.40400000000005L489.96,287.25800000000004L488.527,289.28200000000004L484.32,292.485Z"
        /**3  重庆   */
        ,
        /**3  重庆   */
        "M318.986,317.871L324.566,315.779L329.13,317.871L330.144,312.06L335.49,308.34L336.841,300.669L340.773,299.60999999999996V292.13999999999993L344.46200000000005,290.7079999999999L352.55800000000005,293.56499999999994L354.22400000000005,295.82499999999993L358.27500000000003,294.9959999999999L360.77500000000003,295.2339999999999L363.39400000000006,298.6859999999999L364.46400000000006,305.2339999999999L363.63200000000006,307.4959999999999L361.96600000000007,306.90199999999993C361.96600000000007,306.90199999999993,358.89400000000006,310.4029999999999,357.08200000000005,310.71399999999994C355.273,311.0249999999999,348.74600000000004,312.1429999999999,348.74600000000004,312.1429999999999L346.48600000000005,314.4059999999999L348.26900000000006,316.6679999999999L348.50900000000007,321.5509999999999L350.7710000000001,322.14799999999985L356.48600000000005,329.28999999999985L356.8430000000001,338.69699999999983L353.62500000000006,341.1089999999998L353.0330000000001,341.5539999999998L349.69900000000007,338.1029999999998L346.4840000000001,333.81299999999976L346.2440000000001,331.19399999999973L343.9840000000001,330.71799999999973L341.3630000000001,331.55199999999974L337.07600000000014,329.52699999999976L335.05400000000014,334.40999999999974L331.36300000000017,334.6489999999997L328.74400000000014,338.6979999999997L327.07800000000015,337.8639999999997L324.7980000000002,330.8869999999997L318.29100000000017,326.8169999999997L318.986,317.871Z"
        /**4  河北   */
        ,
        /**4  河北   */
        "M413.04,235.229L413.39700000000005,233.80300000000003L411.61400000000003,230.35000000000002L418.516,217.85000000000002C418.516,217.85000000000002,427.24100000000004,209.95000000000002,424.829,212.13200000000003C422.418,214.31700000000004,429.35200000000003,210.94400000000005,429.35200000000003,210.94400000000005L433.62,205.52100000000004L431.973,204.39600000000004L430.413,200.48900000000003L427.094,201.77500000000003L421.615,200.34700000000004L421.378,198.91900000000004L421.14,189.74900000000005L424.83,188.08200000000005L424.411,186.51900000000006L424.234,186.41500000000005L425.044,182.85800000000006L419.95,184.98600000000005L420.782,186.89100000000005L420.96,188.31500000000005L421.14,189.74800000000005L418.28299999999996,190.93800000000005L416.49799999999993,192.60500000000005L412.8059999999999,191.53400000000005L407.92499999999995,192.36800000000005L407.09299999999996,190.10400000000004L407.68699999999995,185.81700000000004L411.37999999999994,181.53100000000003L412.21099999999996,176.65100000000004L415.90199999999993,172.96000000000004L422.5679999999999,176.41400000000004H424.23599999999993L425.42499999999995,181.17600000000004L427.3299999999999,182.12600000000003L428.2829999999999,185.22600000000003L427.9269999999999,187.25000000000003L431.97399999999993,190.10400000000004L432.5679999999999,192.36800000000005L435.90599999999995,193.79600000000005L444.23799999999994,189.27300000000005V186.65200000000004L449.1209999999999,179.50900000000004L445.67099999999994,174.62800000000004L443.04999999999995,174.39000000000004L438.287,171.17200000000005L439.955,166.05400000000006L432.568,165.45900000000006L429.35499999999996,160.69400000000007L429.712,158.07500000000007L423.402,151.16900000000007L419.351,153.19500000000008L415.9,156.64700000000008L417.09099999999995,159.26700000000008L416.25699999999995,160.93400000000008L411.37499999999994,161.17100000000008L409.11099999999993,163.19300000000007L407.08899999999994,162.35800000000006L405.0679999999999,164.38400000000007L400.54099999999994,167.83700000000007L398.51699999999994,166.40700000000007V161.76300000000006L396.85099999999994,160.93100000000007L394.2319999999999,162.12000000000006L391.1359999999999,168.66700000000006L389.9469999999999,174.97800000000007L393.6359999999999,181.16800000000006L396.8509999999999,184.02600000000007V189.26600000000008L398.7549999999999,193.55200000000008L397.9209999999999,198.3160000000001L393.03699999999986,201.52900000000008L390.7769999999999,208.9110000000001L394.82599999999985,213.5560000000001L397.6829999999999,219.2730000000001L395.89799999999985,222.1300000000001L395.4209999999999,226.0580000000001L393.6339999999999,228.6770000000001L392.7999999999999,231.53600000000012L395.4209999999999,234.9820000000001L406.8509999999999,236.41300000000012L411.3749999999999,234.62600000000012L413.04,235.229Z"
        /**5  山西   */
        ,
        /**5  山西   */
        "M363.393,259.519L371.60999999999996,257.254L381.967,250.11200000000002L389.34799999999996,248.681L394.82499999999993,244.394L395.4239999999999,234.989L392.80099999999993,231.54L393.63699999999994,228.684L395.4239999999999,226.065L395.9009999999999,222.136L397.6859999999999,219.27599999999998L394.82699999999994,213.563L390.7799999999999,208.91799999999998L393.0419999999999,201.53499999999997L397.92799999999994,198.32299999999998L398.7579999999999,193.558L396.8539999999999,189.272V184.03L393.63899999999995,181.176L386.2579999999999,184.98499999999999L385.06699999999995,183.796L381.13699999999994,186.65099999999998L377.9239999999999,186.41599999999997L371.6119999999999,195.46399999999997H369.7059999999999L366.2539999999999,198.32199999999997L366.49099999999993,202.36799999999997L364.8209999999999,205.81999999999996L364.2269999999999,210.34499999999997L361.1309999999999,215.22899999999998L364.4649999999999,222.134L363.3949999999999,226.063L360.7759999999999,230.349C360.7759999999999,230.349,364.9219999999999,249.345,364.4649999999999,247.25199999999998S361.9669999999999,256.06199999999995,361.9669999999999,256.06199999999995L363.393,259.519Z"
        /**6  辽宁   */
        ,
        /**6  辽宁   */
        "M491.15,173.2L497.933,163.19799999999998L502.21999999999997,158.31699999999998L501.62499999999994,153.55399999999997L497.10099999999994,148.31499999999997L496.50699999999995,144.02899999999997L488.29099999999994,132.95399999999998L487.93299999999994,134.028L486.26699999999994,135.814L482.81399999999996,131.76399999999998L477.931,130.33499999999998L477.695,131.76399999999998V134.028L475.673,136.04999999999998L471.626,140.1H467.1L465.434,142.956H463.64500000000004L460.55100000000004,146.052H458.76400000000007L455.0730000000001,149.743L452.8110000000001,150.339L447.93000000000006,157.839L444.83400000000006,153.195L441.3810000000001,150.933L439.7150000000001,152.6L441.6180000000001,162.602L439.9520000000001,166.055L438.2840000000001,171.175L443.0470000000001,174.39000000000001L445.66800000000006,174.62800000000001L449.11800000000005,179.50900000000001L451.61800000000005,178.08C451.61800000000005,178.08,454.4750000000001,175.199,455.66800000000006,173.198C456.86000000000007,171.196,459.71700000000004,166.41,459.71700000000004,166.41L466.504,164.981L470.791,169.267L467.692,176.054L463.64300000000003,182.365L467.331,184.985L467.098,188.08300000000003L464.241,190.93800000000002L464.83799999999997,192.12800000000001L469.71899999999994,189.50900000000001L476.8619999999999,180.102L487.6979999999999,174.03L491.15,173.2Z"
        /**7  黑龙江  */
        ,
        /**7  黑龙江  */
        "M464.838,96.639L471.625,95.449L474.479,100.69L478.764,103.785L481.62,102.597H484.00600000000003L488.29100000000005,100.09599999999999L491.38500000000005,103.18999999999998L493.40900000000005,103.54699999999998L498.7660000000001,101.52399999999999L502.57900000000006,103.54699999999998L504.24500000000006,107.83499999999998H507.1020000000001L508.5320000000001,109.73899999999998L512.2210000000001,113.78799999999998L513.6470000000002,112.95499999999998L513.0530000000001,107.83499999999998L515.0790000000001,106.40299999999998L517.9330000000001,112.11899999999997L520.5540000000001,113.19299999999997L523.412,116.40499999999997L525.433,116.04799999999997L526.269,114.62099999999997L530.792,109.50099999999996L532.8140000000001,110.92899999999996L534.244,108.90699999999995L535.6750000000001,111.52599999999995L539.9580000000001,112.95499999999996H542.8180000000001L544.8880000000001,113.04299999999995L543.6500000000001,110.92999999999995L543.0520000000001,104.02399999999994L537.9370000000001,96.04599999999995L540.7920000000001,93.18899999999995L543.4080000000001,88.30599999999995H553.0540000000001L554.839,86.64099999999995L554.2420000000001,82.95099999999995L556.267,79.25999999999995L555.671,77.23599999999995L556.503,73.78499999999995L556.267,56.04299999999995L559.1220000000001,50.327999999999946L555.908,46.635999999999946L556.503,44.37499999999994L555.076,42.35099999999994L551.386,43.779999999999944L547.097,48.663999999999945L542.814,50.68699999999995L538.525,56.63799999999995L527.927,60.32999999999995L523.048,56.63799999999995L523.642,54.37599999999995L521.142,50.68699999999995L519.951,46.87599999999995L515.904,46.63699999999995L508.759,42.94699999999995L505.90000000000003,44.01799999999995L502.57000000000005,42.35099999999995L497.68300000000005,43.18499999999995L493.40000000000003,41.75599999999995L490.77900000000005,38.06599999999995L488.28100000000006,35.20899999999995L487.33000000000004,32.351999999999954L483.99600000000004,28.899999999999956L481.97,25.800999999999956L477.326,19.490999999999957L475.898,15.800999999999958L470.779,9.252999999999957L469.347,5.798999999999957L462.798,2.5829999999999567L458.511,4.011999999999957L454.822,3.1789999999999567L446.486,1.5109999999999568L435.416,5.442999999999957L433.392,7.228999999999957L435.654,10.324999999999957L432.798,17.823999999999955L433.632,18.658999999999956L438.51300000000003,21.754999999999956L441.134,17.46899999999996L445.658,20.32499999999996L445.423,22.34699999999996L447.087,27.46599999999996L449.941,30.68399999999996L455.65799999999996,31.516999999999957L457.32599999999996,29.729999999999958L460.777,29.252999999999957L467.324,23.77699999999996L475.90000000000003,30.086999999999957L473.04200000000003,41.75599999999996L473.636,50.088999999999956V55.207999999999956L471.37600000000003,56.39899999999996L471.13800000000003,69.73399999999995L470.54100000000005,69.25799999999995L468.28100000000006,66.39999999999995H467.08900000000006L466.494,67.47299999999994C466.494,67.47299999999994,457.697,80.51699999999994,459.348,78.06899999999995C461,75.62099999999995,455.897,82.59199999999994,455.897,82.59199999999994L456.254,84.01999999999994L463.399,88.90599999999993L467.325,87.83499999999994L467.924,88.90599999999993L467.09,90.09499999999994L463.40099999999995,91.76199999999994L463.042,94.97599999999994L464.838,96.639Z"
        /**8  吉林   */
        ,
        /**8  吉林   */
        "M544.896,113.042L542.8259999999999,112.95400000000001H539.968L535.683,111.52300000000001L534.253,108.90400000000001L532.822,110.92800000000001L530.8,109.50000000000001L526.2769999999999,114.62000000000002L525.443,116.04700000000003L523.4209999999999,116.40400000000002L520.5619999999999,113.19000000000003L517.9409999999999,112.11800000000002L515.0869999999999,106.40300000000002L513.0599999999998,107.83400000000002L513.6539999999999,112.95400000000002L512.2279999999998,113.78700000000002L508.5389999999998,109.73700000000002L507.1069999999998,107.83400000000002H504.2529999999998L502.5869999999998,103.54600000000002L498.77399999999983,101.52300000000002L493.41999999999985,103.54600000000002L491.39499999999987,103.18900000000002L488.29699999999985,100.09500000000003L484.01199999999983,102.59500000000003H481.62899999999985L478.7719999999998,103.78600000000003L474.4869999999998,100.69000000000003L471.6329999999998,95.45000000000003L464.84599999999983,96.63900000000004L462.22499999999985,99.73800000000004L461.98699999999985,103.18800000000005L454.48499999999984,101.16500000000005L453.41099999999983,103.54600000000005L454.01199999999983,105.21300000000005L457.9399999999998,108.07200000000005V112.11800000000005L458.5339999999998,116.04700000000005L460.7989999999998,119.50300000000006L461.1549999999998,122.59800000000006L462.8209999999998,123.78900000000006L468.5379999999998,118.31000000000006L474.49099999999976,125.81200000000005V130.10000000000005L477.7039999999998,131.76700000000005L477.9419999999998,130.33600000000004L482.82699999999977,131.76700000000005L486.2779999999998,135.81300000000005L487.9439999999998,134.02900000000005L488.3009999999998,132.95500000000004L496.5179999999998,144.03000000000003L497.1119999999998,148.31600000000003L501.6389999999998,153.55500000000004L502.23099999999977,158.31600000000003L506.28199999999975,155.81700000000004L509.9709999999998,145.21900000000002L511.6409999999998,144.62400000000002L515.6879999999998,146.88700000000003L522.2369999999997,146.05300000000003L524.4969999999997,144.02900000000002L521.4049999999997,139.26600000000002L522.2369999999997,138.07500000000002C522.2369999999997,138.07500000000002,530.0769999999998,135.46400000000003,528.3089999999997,136.05300000000003C526.5429999999998,136.64100000000002,530.8089999999997,131.17000000000002,530.8089999999997,131.17000000000002L534.0239999999998,129.74200000000002L534.2619999999998,124.97600000000001L535.0939999999998,121.76400000000001L536.8789999999998,121.168L538.5469999999998,122.95700000000001L540.2149999999998,124.38300000000001L544.5019999999998,118.668L545.6899999999998,114.38000000000001L544.896,113.042Z"
        /**9  江苏   */
        ,
        /**9  江苏   */
        "M483.646,282.616L482.22,286.902L480.322,289.15299999999996L477.097,292.977L472.21799999999996,292.142L468.289,289.759L465.906,290.949L457.335,290.117L457.097,286.663L452.81,283.807L451.382,281.189L453.402,278.925V277.26V274.993L458.52299999999997,274.399L458.763,272.376L457.097,270.113L454.476,269.758L452.81,272.61199999999997L448.523,271.78099999999995L447.33500000000004,269.518L444.478,267.852L445.433,261.77799999999996L444.835,260.70599999999996L441.976,261.18499999999995L439.955,259.518L434.837,258.089L431.976,255.232L425.669,252.611L426.26099999999997,249.755L428.76099999999997,248.686L433.40599999999995,252.613H434.83899999999994L439.12299999999993,252.137L441.62299999999993,250.115L445.0759999999999,252.971L446.50299999999993,250.351L446.86099999999993,248.921L449.71799999999996,247.254L450.55199999999996,243.804L453.40599999999995,243.207L460.554,248.088C460.554,248.088,463.919,248.84199999999998,465.671,250.113C467.425,251.384,475.437,266.426,475.437,266.426L475.08,268.092L481.628,271.187L483.412,274.046L486.51099999999997,275.47499999999997L487.93899999999996,278.33L485.91599999999994,279.281L482.58199999999994,278.093H477.93699999999995L473.65,276.661L471.984,278.093L475.916,279.281L479.729,280.95L483.646,282.616Z"
        /**10 浙江   */
        ,
        /**10 浙江   */
        "M483.793,336.063L477.338,338.339L473.64500000000004,334.649L471.14700000000005,337.864H467.09800000000007L465.4320000000001,334.40999999999997L463.40900000000005,329.292L459.71700000000004,328.69599999999997L455.67,321.195L453.051,317.505L454.954,315.687L455.67,315.002C455.67,315.002,461.911,306.16200000000003,459.956,308.932C458.002,311.701,459.71700000000004,304.05,459.71700000000004,304.05L461.14700000000005,302.144L464.48300000000006,301.55L465.4340000000001,300.12L464.24500000000006,297.857L465.91100000000006,295.475V290.951L468.2950000000001,289.762L472.22300000000007,292.144L477.1020000000001,292.979L480.3270000000001,289.155L484.3250000000001,292.48699999999997L482.5810000000001,293.811L480.56000000000006,296.907L477.3430000000001,297.859L476.26900000000006,298.692L479.3670000000001,300.358L485.08200000000005,297.859L494.48800000000006,301.78799999999995L495.44100000000003,309.76699999999994H491.632L491.392,312.14899999999994L493.416,315.48099999999994L491.632,317.50499999999994L493.654,320.71899999999994L490.558,324.40899999999993L489.128,322.62199999999996L484.484,334.40999999999997L483.793,336.063Z"
        /**11 安徽   */
        ,
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