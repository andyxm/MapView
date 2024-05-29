package cn.mapview

import android.content.Context
import android.graphics.Path
import android.util.Log
import androidx.core.graphics.PathParser
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGParseException
import java.io.IOException


/**
 * @author
 * data: 2024/5/29 16:04
 * desc:
 */
object SvgParser {

    fun parseSvgPath(context: Context, svgFileName: String?): Path? {
        try {
            // 打开 SVG 文件
            val inputStream = context.assets.open(svgFileName!!)
            // 解析 SVG
            val svg: SVG = SVG.getFromInputStream(inputStream)

            // 创建一个空的 Path 对象
            val path: Path = Path()

            // 获取根元素的子元素
//            val elements: Array<SvgElement> = svg.getRootElement().getChildren()
//
//            // 遍历所有子元素
//            for (element in elements) {
//                // 检查元素是否为路径元素
//                if (element is SVG.Path) {
//                    val svgPath: SVG.Path = element as com.caverock.androidsvg.SVG.Path
//                    // 获取路径数据（d 属性）
//                    val pathData: String = svgPath.getPathData()
//                    // 使用 PathParser 将路径数据解析为 Path 对象
//                    val androidPath: Path = PathParser.createPathFromPathData(pathData)
//                    // 将该 Path 添加到最终的 Path 对象中
//                    path.addPath(androidPath)
//                }
//            }

            return path
        } catch (e: IOException) {
            Log.e("SvgParser", "Error parsing SVG", e)
            return null
        } catch (e: SVGParseException) {
            Log.e("SvgParser", "Error parsing SVG", e)
            return null
        }
    }
}