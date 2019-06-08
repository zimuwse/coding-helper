package org.muzi.open.helper.service.tplengine.velocity;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.muzi.open.helper.util.StringUtil;

import java.io.IOException;
import java.io.Writer;

/**
 * @author: muzi
 * @time: 2019-06-07 18:11
 * @description:
 */
public class CapFirstFunc extends Directive {
    @Override
    public String getName() {
        return "cap_first";
    }

    @Override
    public int getType() {
        return LINE;
    }

    @Override
    public boolean render(InternalContextAdapter internalContextAdapter, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        writer.write(StringUtil.upperFirst(node.jjtGetChild(0).value(internalContextAdapter).toString()));
        return true;
    }
}
