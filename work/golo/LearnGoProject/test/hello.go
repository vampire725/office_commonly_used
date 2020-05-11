// helloworld.go
package main

import "fmt"

//func main() {
//	fmt.Println("Hello World!")
//	fmt.Println(mathClass.Add(1, 1))
//	fmt.Println(mathClass.Sub(1, 1))
//
//	//// 声明两个变量并初始化
//	//var a,f string = "Runoob","out"
//	//fmt.Println(a,f)
//	//
//	//// 没有初始化就为零值
//	//var d int
//	//fmt.Println(d)
//	//
//	//var b, c int = 1, 2
//	//fmt.Println(b, c)
//	//
//	//// bool 零为false
//	//var u bool
//	//fmt.Println(u)
//
//	var i int
//	var f float64
//	var b bool
//	var s string
//	fmt.Printf("%v %v %v %q\n", i, f, b, s)
//
//	a_test := "a_test1"
//	fmt.Println(a_test)
//
//	a_int := 2
//	fmt.Println(a_int)
//
//	_, numb, strs := numbers()
//	fmt.Println(numb, strs)
//}
//
//func numbers() (int, int, string) {
//	t, y, i := 1, 2, "str"
//	return t, y, i
//}

//func main() {
//	const LENGTH int = 10
//	const WIDTH int = 5
//	var area int
//	const a, b, c = 1, false, "str"
//	area = LENGTH + WIDTH
//	fmt.Printf("面积为：%d \n", area)
//	println()
//	println(a, b, c)
//	const (
//		Unknown = 0
//		Female = 1
//		Male = 2
//	)
//	fmt.Println(Unknown,Female,Male)
//
//}

// iota用法

func main() {
	const (
		a = iota
		b
		c
		d = "ha"
		e
		f = 100
		g
		h = iota
		i
		j = "j"
		k
		m = iota
		p
	)
	fmt.Println(a, b, c, d, e, f, g, h, i, j, k, m, p)
}
