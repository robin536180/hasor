/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.hasor.test.actions.mapping;
import net.hasor.web.annotation.Get;
import net.hasor.web.annotation.HttpMethod;
import net.hasor.web.annotation.MappingTo;
import net.hasor.web.annotation.Post;

/**
 * @version : 2017-01-08
 * @author 赵永春 (zyc@hasor.net)
 */
@MappingTo("/mappingto_b.do")
public class MappingAction {
    @Get
    public void execute1() {
    }

    @Post
    public void execute2() {
    }

    @HttpMethod({ "ADD", HttpMethod.DELETE })
    public void execute3() {
    }
}