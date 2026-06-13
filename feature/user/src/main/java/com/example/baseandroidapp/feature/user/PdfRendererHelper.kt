package com.example.baseandroidapp.feature.user

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.util.Base64
import java.io.File

/**
 * Utilitários de conversão e renderização de PDF usando apenas APIs nativas do Android SDK
 * ([PdfRenderer], [Bitmap], [ParcelFileDescriptor], [Base64]). Sem dependências externas.
 *
 * Fluxo: Base64 String -> ByteArray -> arquivo temporário -> renderização das páginas em [Bitmap].
 */

/** Decodifica uma string Base64 para o [ByteArray] correspondente ao conteúdo do PDF. */
fun decodeBase64Pdf(base64: String): ByteArray =
    Base64.decode(base64, Base64.DEFAULT)

/**
 * Renderiza todas as páginas do PDF informado em [pdfData] para uma lista de [Bitmap].
 *
 * Escreve os bytes em um arquivo temporário no cacheDir, abre via [PdfRenderer] e renderiza
 * cada página dimensionada pela densidade da tela. O arquivo temporário é sempre removido ao final.
 *
 * @throws java.io.IOException se o PDF não puder ser lido/renderizado.
 */
fun renderPdfToBitmaps(context: Context, pdfData: ByteArray): List<Bitmap> {
    val tempFile = File.createTempFile("pdf", ".pdf", context.cacheDir)
    try {
        tempFile.outputStream().use { it.write(pdfData) }

        ParcelFileDescriptor.open(tempFile, ParcelFileDescriptor.MODE_READ_ONLY).use { fd ->
            PdfRenderer(fd).use { renderer ->
                val densityDpi = context.resources.displayMetrics.densityDpi
                return (0 until renderer.pageCount).map { index ->
                    renderer.openPage(index).use { page ->
                        // 72 = pontos por polegada do PDF; escala para a densidade da tela.
                        val width = densityDpi * page.width / 72
                        val height = densityDpi * page.height / 72
                        Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
                            // PdfRenderer não pinta o fundo; garante branco para áreas transparentes.
                            eraseColor(Color.WHITE)
                            page.render(this, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                        }
                    }
                }
            }
        }
    } finally {
        if (tempFile.exists() && !tempFile.delete()) {
            tempFile.deleteOnExit()
        }
    }
}
