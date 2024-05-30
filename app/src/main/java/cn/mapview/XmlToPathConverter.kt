package cn.mapview

import android.content.Context
import android.graphics.Path
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.util.Log
import androidx.annotation.RawRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.PathParser
import org.w3c.dom.Element
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Field
import javax.xml.parsers.DocumentBuilderFactory


/**
 * @author
 * data: 2024/5/29 17:00
 * desc:
 */
object XmlToPathConverter {

    fun getResId(index: Int): Int {
        return when (index) {
            0 -> R.drawable.path0
            1 -> R.drawable.path1
            2 -> R.drawable.path2
            3 -> R.drawable.path3
            4 -> R.drawable.path4
            5 -> R.drawable.path5
            6 -> R.drawable.path6
            7 -> R.drawable.path7
            8 -> R.drawable.path8
            9 -> R.drawable.path9
            10 -> R.drawable.path10
            else -> {
                R.drawable.path0
            }
        }
    }

    fun convertXmlToPath(context: Context?, index: Int): Path? {
        try {
            val drawableResId = getResId(index)
            // 从资源中获取Drawable对象
            val drawable = ContextCompat.getDrawable(context!!, drawableResId)
            // 获取Drawable对象的路径数据（pathData）
            val pathData = drawableToPathData(drawable)
            // 使用PathParser将路径数据解析为Path对象
            val path: Path = PathParser.createPathFromPathData(pathData)
            return path
        } catch (e: Exception) {
            Log.e("XmlToPathConverter", "Error converting XML to Path", e)
            return null
        }
    }

    private fun drawableToPathData(drawable: Drawable?): String {
        // 在这里根据drawable类型，如VectorDrawable和AnimatedVectorDrawable，进行不同的处理
        if (drawable is VectorDrawable) {
            try {
                // 使用反射获取VectorDrawable的路径数据
                val field: Field = VectorDrawable::class.java.getDeclaredField("mRootGroup")
                field.setAccessible(true)
                val rootGroup: Any = field.get(drawable)
                val pathDataField: Field = rootGroup.javaClass.getDeclaredField("mPathData")
                pathDataField.setAccessible(true)
                return pathDataField.get(rootGroup)?.toString() ?: ""
            } catch (e: java.lang.Exception) {
                Log.e("XmlVectorToPathConverter", "Error getting pathData from VectorDrawable", e)
                return ""
            }
        } else {
            // 对于其他类型的Drawable，您可能需要采取不同的方法来获取路径数据
            // 这里需要根据您的需求和项目中使用的Drawable类型进行适当的处理
            return ""
        }
    }
    fun getRawId(index: Int): Int {
        return when (index) {
            0 -> R.raw.path0
            1 -> R.raw.path1
            2 -> R.raw.path2
            3 -> R.raw.path3
            4 -> R.raw.path4
            5 -> R.raw.path5
            6 -> R.raw.path6
            7 -> R.raw.path7
            8 -> R.raw.path8
            9 -> R.raw.path9
            10 -> R.raw.path10
            else -> {
                R.raw.path0
            }
        }
    }
    fun getXmlValue(context: Context, index: Int): Path? {
        var path: Path? = null
        val rawRes = getRawId(index)
        try {
            // DocumentBuilder对象
            val dbf = DocumentBuilderFactory.newInstance()
            val db = dbf.newDocumentBuilder()
            //打开输入流
            val `is` = context.resources.openRawResource(rawRes)
            // 获取文档对象
            val doc = db.parse(`is`)
            //根节点
            val element = doc.documentElement
            //获取path元素节点集合
            val paths = doc.getElementsByTagName("path")
            for (i in 0 until paths.length) {
                // 取出每一个元素
                val node = paths.item(i) as Element
                //得到android:pathData属性值
                val pathValue = node.getAttribute("android:pathData")
                if (pathValue!=null) {
                    Log.e("TAG", "pathData: $pathValue")
                    path = PathParser.createPathFromPathData(pathValue)
                    break
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.e("TAG", "getXmlValue: ", e)
        }

        return path
    }

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