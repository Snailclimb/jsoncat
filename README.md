## 功能实现

功能实现上，没有参考 Spring 的源码，完全就是按照自己的想法写的。

功能其实写的挺快的，大部分时间都在重构。讲真， 我已经很尽力地保证代码的可读性了。尽量使用最少的代码实现功能， 并且不影响可读性。

目前，我还不是特别满意，欢迎小伙伴们一起来完善啊！人多力量大！

### Get请求和POST请求处理

- [x] `@GetMapping`  : 处理Get请求
- [x] `@PostMapping ` ：处理 Post 请求
- [x] `@RequestBody` : 接收前端传递给后端的json字符串
- [x] `@RequestParam` ：获取Get请求的 URL 查询参数
- [x] `@PathVariable` :  获取 URL 中的参数/占位符

### IOC

- [ ] `@Autowired`  ：注入对象
- [ ] `@Component `：声明对象被 IOC容器管理

### 异常处理

- [ ] `@ControllerAdvice` 
- [ ] `@ExceptionHandler`

### 其他

- [ ] `@SpringBootApplication`
- [ ] `@Configuration`

## 代码质量

- [x] 集成 checkstyle、spotbugs （*遇到了一点小坑，将gradle 版本升级为 6.6.1 解决*）
- [ ] 