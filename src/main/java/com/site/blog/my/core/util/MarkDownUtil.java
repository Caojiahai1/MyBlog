package com.site.blog.my.core.util;

import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.TocBlock;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.ext.toc.internal.TocNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MarkDownUtil {
    /**
     * 转换md格式为html
     *
     * @param markdownString
     * @return
     */
    public static String mdToHtml(String markdownString) {
//        if (StringUtils.isEmpty(markdownString)) {
//            return "";
//        }
//        java.util.List<Extension> extensions = Arrays.asList(TablesExtension.create());
//        Parser parser = Parser.builder().extensions(extensions).build();
//        Node document = parser.parse(markdownString);
//        HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build();
//        String content = renderer.render(document);
//        return content;

//        MutableDataSet options = new MutableDataSet();

         DataHolder OPTIONS = new MutableDataSet().set(Parser.EXTENSIONS, Arrays.asList(
                TocExtension.create(),
                CustomExtension.create(),
                 TablesExtension.create()
        ));

        // uncomment to set optional extensions
        //options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create(), StrikethroughExtension.create()));

        // uncomment to convert soft-breaks to hard breaks
        //options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

        Parser parser = Parser.builder(OPTIONS).build();
//        TocExtension.TOC_CONTENT;
//        Extension extension = TocExtension.create();
//        Parser.addExtensions(options, extension);

        HtmlRenderer renderer = HtmlRenderer.builder(OPTIONS).build();

        // You can re-use parser and renderer instances
        Node document = parser.parse(markdownString);
        String html = renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
//        System.out.println(html);
        return html;
    }

    static class CustomNodeRenderer implements NodeRenderer {
        public static class Factory implements DelegatingNodeRendererFactory {
            @Override
            public NodeRenderer apply(DataHolder options) {
                return new CustomNodeRenderer();
            }

            @Override
            public Set<Class<? extends NodeRendererFactory>> getDelegates() {
                Set<Class<? extends NodeRendererFactory>> set = new HashSet<Class<? extends NodeRendererFactory>>();
                // add node renderer factory classes to which this renderer will delegate some of its rendering
                // core node renderer is assumed to have all depend it so there is no need to add it
                set.add(TocNodeRenderer.Factory.class);
                return set;

                // return null if renderer does not delegate or delegates only to core node renderer
                //return null;
            }
        }

        @Override
        public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
            HashSet<NodeRenderingHandler<?>> set = new HashSet<NodeRenderingHandler<?>>();
            set.add(new NodeRenderingHandler<TocBlock>(TocBlock.class, new com.vladsch.flexmark.html.CustomNodeRenderer<TocBlock>() {
                @Override
                public void render(TocBlock node, NodeRendererContext context, HtmlWriter html) {
                    // test the node to see if it needs overriding
                    NodeRendererContext subContext = context.getDelegatedSubContext(true);
                    subContext.delegateRender();
//                    String tocText = subContext.getHtmlWriter().toString(0);

                    // output to separate stream
//                    System.out.println("---- TOC HTML --------------------");
//                    System.out.print(tocText);
//                    System.out.println("----------------------------------\n");

                    html.tagLineIndent("div", () -> html.append(subContext.getHtmlWriter()));
                }
            }));

            return set;
        }
    }

    static class CustomExtension implements HtmlRenderer.HtmlRendererExtension {
        @Override
        public void rendererOptions(MutableDataHolder options) {

        }

        @Override
        public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
            rendererBuilder.nodeRendererFactory(new CustomNodeRenderer.Factory());
        }

        static Extension create() {
            return new CustomExtension();
        }
    }
}
