package top.rechinx.meow.glide

import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.Excludes
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.module.AppGlideModule
import top.rechinx.meow.App
import top.rechinx.meow.core.source.SourceManager
import top.rechinx.meow.data.cache.CoverCache
import top.rechinx.meow.data.catalog.model.InternalCatalog
import top.rechinx.meow.di.modules.GlideDaggerModule
import top.rechinx.meow.domain.manga.model.Manga
import java.io.InputStream
import javax.inject.Inject

@GlideModule
class ClientGlideModule : AppGlideModule() {

    @Inject lateinit var sourceManager: SourceManager

    @Inject lateinit var coverCache: CoverCache

    init {
        App.get().appComponent()
                .glideComponentBuilder()
                .build()
                .inject(this)
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {

        builder.setDiskCache(InternalCacheDiskCacheFactory(context, 50 * 1024 * 1024))
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val internalCatalogFactory = InternalCatalogModelLoader.Factory()
        val mangaFactory = MangaModelLoader.Factory(sourceManager, coverCache)

        registry.append(InternalCatalog::class.java, Drawable::class.java, internalCatalogFactory)
        registry.append(Manga::class.java, InputStream::class.java, mangaFactory)
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}