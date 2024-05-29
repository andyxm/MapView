package cn.mapview

import android.content.Context
import android.graphics.Path
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.graphics.PathParser
import java.lang.reflect.Field


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
}