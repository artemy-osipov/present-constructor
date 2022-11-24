package io.github.artemy.osipov.shop

import fr.opensagres.xdocreport.document.docx.textstyling.DocxDefaultStylesGenerator
import fr.opensagres.xdocreport.template.freemarker.internal.XDocFreemarkerContext
import io.github.artemy.osipov.shop.service.candy.Candy
import io.github.artemy.osipov.shop.service.report.ReportPresent
import io.sentry.HubAdapter
import io.sentry.IHub
import org.springframework.aot.hint.MemberCategory
import org.springframework.aot.hint.RuntimeHints
import org.springframework.aot.hint.RuntimeHintsRegistrar
import java.time.Instant
import java.time.ZonedDateTime
import java.util.ServiceLoader

class AotHints: RuntimeHintsRegistrar {

    override fun registerHints(hints: RuntimeHints, classLoader: ClassLoader?) {
        // report
        hints.reflection().registerType(ServiceLoader::class.java, MemberCategory.INVOKE_PUBLIC_METHODS)
        hints.reflection().registerType(DocxDefaultStylesGenerator::class.java, MemberCategory.INVOKE_PUBLIC_METHODS)
        hints.reflection().registerType(XDocFreemarkerContext::class.java, MemberCategory.INVOKE_PUBLIC_METHODS)
        hints.resources().registerPattern("fr/opensagres/xdocreport/document/docx/textstyling/*.xml")
        hints.resources().registerPattern("freemarker/ext/beans/DefaultMemberAccessPolicy-rules")

        hints.reflection().registerType(ReportPresent::class.java, MemberCategory.INVOKE_PUBLIC_METHODS)
        hints.reflection().registerType(ReportPresent.Item::class.java, MemberCategory.INVOKE_PUBLIC_METHODS)

        hints.reflection().registerType(Candy::class.java, MemberCategory.INVOKE_PUBLIC_METHODS)

        // sentry
        hints.reflection().registerType(IHub::class.java, MemberCategory.INVOKE_PUBLIC_METHODS)
        hints.reflection().registerType(HubAdapter::class.java, MemberCategory.INVOKE_PUBLIC_METHODS)

        // spring-data
        hints.reflection().registerType(Array<Instant>::class.java)
        hints.reflection().registerType(Array<ZonedDateTime>::class.java)
    }
}
