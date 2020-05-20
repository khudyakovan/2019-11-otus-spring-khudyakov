package ru.otus.graduation.pos.shell;

import org.jline.terminal.Terminal;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;
import org.springframework.stereotype.Component;
import ru.otus.graduation.dto.SaleDto;
import ru.otus.graduation.model.Order;
import ru.otus.graduation.model.Product;
import ru.otus.graduation.service.master.ProductService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class ShellHelper {

    private final Terminal terminal;
    private final ProductService productService;

    public ShellHelper(@Lazy Terminal terminal, ProductService productService) {
        this.terminal = terminal;
        this.productService = productService;
    }

    public LinkedHashMap<String, Object> getTableHeader() {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "PLU");
        headers.put("name", "Название");
        headers.put("quantity", "Количество");
        headers.put("price", "Цена");
        headers.put("summary", "Сумма");
        return headers;
    }

    public List<SaleDto> getSaleDtoList(Order order) {
        List<String> ids = new ArrayList<>(order.getDetails().keySet());
        List<Product> products = productService.findByIdIsIn(ids);
        List<SaleDto> dtos = new ArrayList<>();

        order.getDetails().entrySet().forEach(entry -> {
            SaleDto itemDto = new SaleDto();
            itemDto.setId(entry.getKey());
            Product product = products.stream()
                    .filter(p -> entry.getKey().equals(p.getId()))
                    .findFirst().get();
            itemDto.setPrice(product.getPrice());
            itemDto.setName(product.getName());
            itemDto.setQuantity(entry.getValue());
            itemDto.setSummary(product.getPrice() * entry.getValue());
            dtos.add(itemDto);
        });
        return dtos;
    }

    public void print(String message) {
        terminal.writer().println(message);
        terminal.flush();
    }

    public void render(List list, LinkedHashMap<String, Object> headers) {
        TableModel model = new BeanListTableModel<>(list, headers);
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addInnerBorder(BorderStyle.oldschool);
        tableBuilder.addHeaderBorder(BorderStyle.oldschool);
        print(tableBuilder.build().render(250));
    }

    public float getTotal(List<SaleDto> list){
        return list.stream().reduce(0f, (acc, item) -> acc + item.getSummary(), Float::sum);
    }
}
