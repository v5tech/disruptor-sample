/**
 * disruptor 链式
 *
 * SingleChainApp disruptor链式依赖，单链。按顺序执行
 *
 * MultiChainApp disruptor链式依赖，多个链。chain1和chain2之间没有依赖关系，并行执行，但chain1中的step1-1和step1-2有依赖关系顺序执行。chain2同理
 *
 */
package net.ameizi.disruptor.samples.chain;