# DesensitiveModule

# in pojo class which field need to de-sensitive 
add annotation DesensitiveField

value :

    
     ID_CARD("身份证"),
    
    ADDRESS("地址"),
    
    NAME("姓名"),
    
    PHONE("手机号"),
    
    EMAIL("邮箱"),
    
    BANK_CARD_NO("银行卡号"),
    
    RECHARGE_ADDRESS("存币地址");

## Example

```Java
@DesensitiveField(SensitiveType.RECHARGE_ADDRESS)
private String rechargeAddress;
```

# in Method add annotation by rerutn type

## return type 
### Result<Page>...
use DesensitiveResult annotation

### Page<>...
use DesensitivePage annotation

### List<>
DesensitiveList

## Example

```
@DesensitiveResult
@GetMapping("testResult")
public Result<Page<RechargeRecordDTO>> testResult(){
    List<RechargeRecordDTO> list = new ArrayList<>();
    list.add(RechargeRecordDTO.builder().rechargeAddress("86:13246628423").name("老笨蛋").build());
    Page<RechargeRecordDTO> tempPage = new Page<>();
    tempPage.setRecords(list);
    return ResultUtil.success(tempPage);
```

```
@DesensitivePage
@GetMapping("testPage")
public Page<RechargeRecordDTO> testPage(){
    List<RechargeRecordDTO> list = new ArrayList<>();
    list.add(RechargeRecordDTO.builder().rechargeAddress("86:13246628423").name("老笨蛋").build());
    Page<RechargeRecordDTO> tempPage = new Page<>();
    tempPage.setRecords(list);
    return tempPage;
```

```
@DesensitiveList
@GetMapping("testList")
public  List<RechargeRecordDTO> testList(){
    List<RechargeRecordDTO> list = new ArrayList<>();
    list.add(RechargeRecordDTO.builder().rechargeAddress("86:13246628423").name("老笨蛋").build());
    return list;
```
