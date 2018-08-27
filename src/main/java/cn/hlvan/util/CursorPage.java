package cn.hlvan.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CursorPage<E> {

    private final List<E> content;

    private final boolean hasMore;

}
