package io.github.artemy.osipov.shop

import io.github.artemy.osipov.shop.service.report.ReportPresent
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.nativex.hint.NativeHint
import org.springframework.nativex.hint.ResourceHint
import org.springframework.nativex.hint.TypeAccess
import org.springframework.nativex.hint.TypeHint

@NativeHint(
    types = [
        TypeHint(
            types = [
                java.util.ServiceLoader::class,
                fr.opensagres.xdocreport.document.docx.textstyling.DocxDefaultStylesGenerator::class,
                fr.opensagres.xdocreport.template.freemarker.internal.XDocFreemarkerContext::class,
            ],
            access = [TypeAccess.PUBLIC_METHODS]
        ),
    ],
    resources = [ResourceHint(
        patterns = [
            "fr/opensagres/xdocreport/document/docx/textstyling/.*\\.xml$",
            "freemarker/ext/beans/DefaultMemberAccessPolicy-rules",
        ]
    )]
)
@TypeHint(
    types = [
        ReportPresent::class,
        ReportPresent.Item::class
    ],
    access = [TypeAccess.PUBLIC_METHODS]
)
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
