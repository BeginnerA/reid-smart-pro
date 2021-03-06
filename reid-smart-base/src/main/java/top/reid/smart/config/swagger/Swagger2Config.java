package top.reid.smart.config.swagger;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.SpringBootVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Swagger2 配置类
 *
 * @author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2021/12/30
 * @Version V1.0
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class Swagger2Config extends SwaggerProperties implements WebMvcConfigurer {

    /**
     * 显示 swagger-ui.html 文档展示页，还必须注入 swagger 资源：
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * swagger2 的配置文件，这里可以配置 swagger2 的一些基本的内容，比如扫描的包等等
     *
     * @return Docket
     */
    @Bean
    public Docket defaultApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 将api的元信息设置为包含在json ResourceListing响应中。
                .apiInfo(apiInfo())
                .groupName(this.getApplicationName() + this.getApplicationVersion() + "版本")
                .select()
                // 此包路径下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage(this.getBasePackage()))
                // 加了 ApiOperation 注解的类，才生成接口文档
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                // 授权信息设置，必要的 header token 等认证信息
                .securitySchemes(securitySchemes())
                // 授权信息全局应用
                .securityContexts(securityContexts());
    }

    /**
     * 设置授权信息
     */
    private List<SecurityScheme> securitySchemes() {
        return Collections.singletonList(new ApiKey("X-Access-Token", "X-Access-Token", "header"));
    }

    /**
     * 新增 securityContexts 保持登录状态
     */
    private List<SecurityContext> securityContexts() {
        return new ArrayList<SecurityContext> (
                Collections.singleton(SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .build())
        );
    }

    /**
     * 默认身份验证
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return new ArrayList<SecurityReference> (
                Collections.singleton(new SecurityReference("X-Access-Token", authorizationScopes)));
    }

    /**
     * api文档的详细信息函数,注意这里的注解引用的是哪个
     *
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 大标题
                .title(this.getApplicationName() + "后台服务API接口文档")
                // 版本号
                .version("Application Version: " + this.getApplicationVersion() + ", Spring Boot Version: " + SpringBootVersion.getVersion())
				.termsOfServiceUrl(this.getTermsOfServiceUrl())
                // 描述
                .description(this.getApplicationDescription())
                // 作者
                .contact(new Contact(this.getAuthorName(), this.getContactUrl(), this.getContactEmail()))
                .license(this.getLicense())
                .licenseUrl(this.getLicenseUrl())
                .build();
    }

}
